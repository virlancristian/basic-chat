package net.main;

public class Main {
    public static void main(String[] args) {
        if(!args[0].equals("build") && !args[0].equals("clean")) {
            throw new IllegalArgumentException("Invalid build phase!");
        }

        new App(args[0].equals("build") ? 1 : 2).run();
    }
}
