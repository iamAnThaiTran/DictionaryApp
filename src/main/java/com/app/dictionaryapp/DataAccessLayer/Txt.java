package com.app.dictionaryapp.DataAccessLayer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Txt {
    private String pathFile; // luu path file
    private FileWriter fileWriter; // ghi vao file
    private File file; // lay noi dung cua file

    public Txt(String pathFile) {
        this.pathFile = pathFile;
    }

    public void connect() {
        try {
            file = new File(pathFile);
            fileWriter = new FileWriter(file, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String text) {
        try {
            fileWriter.write(text + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleleTextInFile(String text) {
        ObservableList<String> observableList = getContentInFile();

        for(String line: observableList) {
            if (!line.equals(text)) {
                try {
                    fileWriter.write(line + "\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ObservableList<String> getContentInFile() {
        ObservableList<String> observableList = FXCollections.observableArrayList();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine() && !scanner.nextLine().equals("")) {
                observableList.add(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return observableList;
        }
    }
}
