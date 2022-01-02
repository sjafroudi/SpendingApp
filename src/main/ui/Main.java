package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new SpendingApp();
        } catch (FileNotFoundException f) {
            System.out.println("File not found");
        }
    }
}
