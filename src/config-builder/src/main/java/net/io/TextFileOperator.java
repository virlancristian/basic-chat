package net.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextFileOperator {
    private File inputFile;

    public TextFileOperator(String path) {
        inputFile = new File(path);
    }

    public void writeSingleLine(String line) {
        try {
            FileWriter writer = new FileWriter(inputFile);

            writer.write(line);
            writer.close();
        } catch (IOException error) {
            System.out.println("Error in TextFileOperator::writeSingleLine - failed to write line in file " + inputFile.getName() + ": " + error);
        }
    }

    public void writeAllLines(List<? extends String> lines) {
        try {
            FileWriter writer = new FileWriter(inputFile);

            for(String line:lines) {
                writer.write(line);
            }

            writer.close();
        } catch(IOException error) {
            System.out.println("Error in TextFileOperator::writeAllLines - failed to write lines in file " + inputFile.getName() + ": " + error);
        }
    }

    public String readSingleLine() {
        String readLine = new String();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));

            readLine = reader.readLine();
        } catch (IOException error) {
            System.out.println("Error in TextFileOperator::readSingleLIne - failed to read line from file " + inputFile.getName() + ": " + error);
        }

        return readLine;
    }

    public List<String> readAllLines() {
        List<String> readLines = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            String buffer = new String();

            while((buffer = reader.readLine()) != null) {
                readLines.add(buffer);
            }
        } catch(IOException error) {
            System.out.println("Error in TextFileOperator::readAllLines - failed to read lines from file " + inputFile.getName() + ": " + error);
        }

        return readLines;
    }
}
