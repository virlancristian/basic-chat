package net.main;

import net.cache.AppCache;
import net.io.InputReader;
import net.io.TextFileOperator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {
    private InputReader inputReader;
    private AppCache appCache;
    private static String BACKEND_PATH;
    private static final String MAVEN_DEPENDENCY_RESOLVE_COMMAND = "cmd /c start maven_dependencies.bat";
    private static final String NODE_DEPENDENCY_RESOLVE_COMMAND = "cmd /c start node_dependencies.bat";
    private static final String MAVEN_PACKAGE_COMMAND = "cmd /c start package.bat";

    public App() {
        inputReader = new InputReader();
        appCache = new AppCache();
        BACKEND_PATH = new TextFileOperator("cache/backend_path.txt").readSingleLine();
    }

    public void run() {
        inputReader.readInput();

        createDatabase();
        initBackendBuild();

        if(inputReader.getBuildType().equals("dev")) {
            runTerminalCommand(NODE_DEPENDENCY_RESOLVE_COMMAND);
        } else {
            runTerminalCommand(MAVEN_PACKAGE_COMMAND);
        }
    }

    private void createDatabase() {
        String databaseName = inputReader.getBuildType().equals("deploy") ? "basicchatdb_production" : "basicchatdb_dev";

        try {
            Process process = Runtime.getRuntime().exec("cmd /c start mysql -hlocalhost -P3306 -u"
                                                        + inputReader.getDbUsername()
            + " -p"
            + inputReader.getDbPassword()
            + " -e \"CREATE DATABASE " + databaseName + "\"");
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
        runTerminalCommand(MAVEN_DEPENDENCY_RESOLVE_COMMAND);
    }

    private void runTerminalCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
        } catch (IOException | InterruptedException error) {
            System.out.println("Error in App::resolveDependencies - failed to resolve dependencies");
        }
    }

    private List<String> overwriteBackendProperties() {
        List<String> overwrittenProperties = new ArrayList<>();
        String username = inputReader.getDbUsername();
        String password = inputReader.getDbPassword();
        String serverIp = inputReader.getServerIp();
        String serverPort = inputReader.getServerPort();
        String databaseName = inputReader.getBuildType().equals("deploy") ? "basicchatdb_production" : "basicchatdb_dev";

        overwrittenProperties.add("spring.datasource.url=jdbc:mysql://localhost:3306/"
                .concat(databaseName)
                .concat("?useSSL=false&allowPublicKeyRetrieval=true\n"));
        overwrittenProperties.add("spring.datasource.username=".concat(username).concat("\n"));
        overwrittenProperties.add("spring.datasource.password=".concat(password).concat("\n"));
        overwrittenProperties.add("server.port=".concat(serverPort).concat("\n"));
        overwrittenProperties.add("microservice.user.endpoint="
                .concat(inputReader.isLocal() ? serverIp.concat(":".concat(serverPort)) : serverIp)
                .concat("/api/user")
                .concat("\n"));

        for(String property:appCache.APPLICATION_PROPERTIES) {
            overwrittenProperties.add(property.concat("\n"));
        }

        return overwrittenProperties;
    }
}
