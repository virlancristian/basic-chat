package net.io;

import java.util.Scanner;

public class InputReader {
    private Scanner reader;
    private String buildType;
    private String dbUsername;
    private String dbPassword;
    private String serverPort;
    private String serverIp;
    private boolean isLocal;

    public InputReader() {
        reader = new Scanner(System.in);
        buildType = null;
        dbUsername = null;
        dbPassword = null;
        serverPort = null;
    }

    public void readInput() {
        readBuildType();
        readDbCredentials();
        readServerPort();
        readServerIp();
    }

    private void readBuildType() {
        System.out.println("Please specify build type (dev / deploy):");

        while(buildType == null || (!buildType.equals("dev") && !buildType.equals("deploy"))) {
            buildType = reader.next();

            if(!buildType.equals("dev") && !buildType.equals("deploy")) {
                System.out.println("Invalid input!");
            }
        }
    }

    private void readDbCredentials() {
        boolean correctInput = false;
        char userConfirmation;

        System.out.println("Please provide database credentials");

        while(!correctInput) {
            System.out.print("Username:");
            dbUsername = reader.next();

            System.out.print("Password:");
            dbPassword = reader.next();

            System.out.println("Are the following credentials correct? (Y / N)");
            userConfirmation = reader.next().toLowerCase().charAt(0);

            while(userConfirmation != 'y' && userConfirmation != 'n') {
                System.out.println("Invalid input!");
                userConfirmation = reader.next().toLowerCase().charAt(0);
            }

            correctInput = userConfirmation == 'y';
        }
    }

    private void readServerPort() {
        char userConfirmation = 'a';

        System.out.println("Run the server on the default port (8080)? (Y / N)");

        while(userConfirmation != 'y' && userConfirmation != 'n') {
            userConfirmation = reader.next().toLowerCase().charAt(0);

            if(userConfirmation != 'y' && userConfirmation != 'n') {
                System.out.println("Invalid input!");
            }
        }

        if(userConfirmation == 'y') {
            serverPort = "8080";
        } else {
            System.out.println("Please provide server port");
            serverPort = reader.next();
        }
    }

    private void readServerIp() {
        char userConfirmation = 'a';

        System.out.println("Would you like to run the server locally? (Y / N)");

        while(userConfirmation != 'y' && userConfirmation != 'n') {
            userConfirmation = reader.next().toLowerCase().charAt(0);

            if(userConfirmation != 'y' && userConfirmation != 'n') {
                System.out.println("Invalid input!");
            }
        }

        if(userConfirmation == 'y') {
            serverIp = "http://" +
                        new TextFileOperator("local_ip.txt").readSingleLine();
            isLocal = true;
        } else {
            System.out.println("Please provide the URL");
            serverIp = reader.next();
            isLocal = false;
        }
    }

    public String getBuildType() {
        return buildType;
    }

    public String getDbUsername() {
        return dbUsername;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getServerPort() {
        return serverPort;
    }

    public String getServerIp() {
        return serverIp;
    }

    public boolean isLocal() {
        return isLocal;
    }
}
