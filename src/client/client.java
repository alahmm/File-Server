package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class client {
    private static final String SERVER_ADDRESS = "192.168.1.29";//you can also set adress of other computer if the server is in another machine
    private static final int SERVER_PORT = 34522;
    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.print("Enter action (1 - get a file, 2 - create a file, 3 - delete a file):");
            Scanner scanner = new Scanner(System.in);
            String option = scanner.next();
            if (option.equals("exit")) {
                output.writeUTF(option);
                System.out.println("The request was sent.");
            } else if (Integer.parseInt(option) == 2) {
                output.writeUTF(option);
                System.out.print(input.readUTF());
                String filename = scanner.next();
                output.writeUTF(filename);
                if (input.readUTF().equals("200")) {
                    System.out.print(input.readUTF());
                    scanner.useDelimiter("\\n");
                    String content = scanner.next();
                    output.writeUTF(content);
                    System.out.println("The request was sent.");
                    System.out.println(input.readUTF());
                } else {
                    System.out.println("The response says that the file already exist!");
                }
            } else if (Integer.parseInt(option) == 1) {
                output.writeUTF(option);
                System.out.print(input.readUTF());
                String filename = scanner.next();
                output.writeUTF(filename);
                System.out.println("The request was sent.");
                if (input.readUTF().equals("200")) {
                    System.out.print("The content of the file is: ");
                    System.out.print(input.readUTF());
                } else {
                    System.out.println("The response says that the file was not found!");
                }
            } else if (Integer.parseInt(option) == 3) {
                output.writeUTF(option);
                System.out.print(input.readUTF());
                String filename = scanner.next();
                output.writeUTF(filename);
                System.out.println("The request was sent.");
                if (input.readUTF().equals("200")) {
                    System.out.print(input.readUTF());
                } else {
                    System.out.println("The response says that the file was not found!");
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
