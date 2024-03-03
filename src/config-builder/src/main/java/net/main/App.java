package net.main;

import net.cache.AppCache;
import net.io.InputReader;
import net.io.TextFileOperator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    private int buildPhase;     //1 - initial build phase, 2 - cleaning after build phase
    private InputReader inputReader;
    private AppCache appCache;
    private static String BACKEND_PATH;
    private static final String MAVEN_DEPENDENCY_RESOLVE_COMMAND = "cmd /c start dependencies.bat";

    public App(int buildPhase) {
        if(buildPhase != 1 && buildPhase != 2) {
            throw new IllegalArgumentException("Invalid build phase!");
        }

        this.buildPhase = buildPhase;
        inputReader = new InputReader();
        appCache = new AppCache();
        BACKEND_PATH = new TextFileOperator("cache/backend_path.txt").readSingleLine();
    }

    public void run() {
        if(buildPhase == 1) {
            build();
        } else {
            clean();
        }
    }

    private void build() {
        inputReader.readInput();
        createDatabase();
        initBackendBuild();
    }

    private void clean() {
        File localIp = new File("local_ip.txt");

        localIp.delete();
    }

    private void createDatabase() {
        try {
            Process process = Runtime.getRuntime().exec("cmd /c start mysql -hlocalhost -P3306 -u"
                                                        + inputReader.getDbUsername()
            + " -p"
            + inputReader.getDbPassword()
            + " -e \"CREATE DATABASE basicchatdb\"");
            process.waitFor();
        } catch (IOException | InterruptedException error) {
            System.out.println("Error in App::createDatabase - unable to run create database command");
        }
    }

    private void initBackendBuild() {
        File resources = new File(BACKEND_PATH + "/src/main/resources");
        File staticDir = new File(BACKEND_PATH + "/src/main/resources/static");
        File templates = new File(BACKEND_PATH + "/src/main/resources/templates");
        List<String> applicationProperties = new ArrayList<>();

        if(!resources.exists()) {
            resources.mkdir();
        }

        if(!staticDir.exists()) {
            staticDir.mkdir();
        }

        if(!templates.exists()) {
            templates.mkdir();
        }

        applicationProperties = overwriteBackendProperties();

        new TextFileOperator(BACKEND_PATH + "/src/main/resources/application.properties").writeAllLines(applicationProperties);
        resolveDependencies();
    }

    private void resolveDependencies() {
        try {
            Process process = Runtime.getRuntime().exec(MAVEN_DEPENDENCY_RESOLVE_COMMAND);
            process.waitFor();
        } catch (IOException | InterruptedException error) {
            System.out.println("Error in App::resolveDependencies - failed to resolve maven dependencies");
        }
    }

    private List<String> overwriteBackendProperties() {
        List<String> overwrittenProperties = new ArrayList<>();
        String username = inputReader.getDbUsername();
        String password = inputReader.getDbPassword();
        String serverIp = inputReader.getServerIp();
        String serverPort = inputReader.getServerPort();

        for(String property:appCache.APPLICATION_PROPERTIES) {
            if(property.contains("spring.datasource.username=")) {
                overwrittenProperties.add("spring.datasource.username=".concat(username).concat("\n"));
            } else if(property.contains("spring.datasource.password=")) {
                overwrittenProperties.add("spring.datasource.password=".concat(password).concat("\n"));
            } else if(property.contains("server.port=")) {
                overwrittenProperties.add("server.port=".concat(serverPort).concat("\n"));
            } else if(property.contains("microservice.user.endpoint=")) {
                overwrittenProperties.add("microservice.user.endpoint="
                        .concat(inputReader.isLocal() ? serverIp.concat(":".concat(serverPort)) : serverIp)
                        .concat("/api/user")
                        .concat("\n"));
            } else {
                overwrittenProperties.add(property.concat("\n"));
            }
        }

        return overwrittenProperties;
    }
}
