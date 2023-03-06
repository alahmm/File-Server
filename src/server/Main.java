package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        List<String> initialArray = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            initialArray.add("file" + i);
        }
        String[] operations = new String[]{"add", "get", "delete", "exit"};
        List<String> serverStorage = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String whatToDo = scanner.next();
            if (whatToDo.equals(operations[0])) {
                String whichFile = scanner.next();
                if (initialArray.contains(whichFile) && !serverStorage.contains(whichFile)) {
                    serverStorage.add(whichFile);
                    System.out.printf("The file %s added successfully%n", whichFile);
                } else {
                    System.out.printf("Cannot add the file %s%n", whichFile);
                }
            } else if (whatToDo.equals(operations[1])) {
                String whichFile = scanner.next();
                if (serverStorage.contains(whichFile)) {
                    System.out.printf("The file %s was sent%n", whichFile);
                } else {
                    System.out.printf("The file %s not found%n", whichFile);
                }
            } else if (whatToDo.equals(operations[2])) {
                String whichFile = scanner.next();
                if (serverStorage.contains(whichFile)) {
                    serverStorage.remove(whichFile);
                    System.out.printf("The file %s was deleted%n", whichFile);
                } else {
                    System.out.printf("The file %s not found%n", whichFile);
                }
            } else if (whatToDo.equals(operations[3])) {
                break;
            }
        }

    }
}