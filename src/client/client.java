package client;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class client {

    static String dirPathToImportFrom = System.getProperty("user.dir") + File.separator + "src" + File.separator + "client" + File.separator + "data" + File.separator;
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
                byte[] message = Files.readAllBytes(Paths.get(dirPathToImportFrom + filename));
                output.writeInt(message.length); // write length of the message
                output.write(message);// write the message
                System.out.print(input.readUTF());

                scanner.useDelimiter("\\n");
                String nameOfNewFile = scanner.next();
                output.writeUTF(nameOfNewFile);

                System.out.println("The request was sent.");
                System.out.printf("Response says that file is saved! ID = %s", input.readUTF().split(" ")[1]);

            } else if (Integer.parseInt(option) == 1) {
                output.writeUTF(option);
                System.out.print(input.readUTF());
                String withIdOrWithName = scanner.next();
                output.writeUTF(withIdOrWithName);

                System.out.print(input.readUTF());
                String IdOrName = scanner.next();
                output.writeUTF(IdOrName);
                System.out.println("The request was sent.");

                String decision = input.readUTF();
                if (decision.equals("200")) {
                    int length = input.readInt();                // read length of incoming message
                    byte[] message = new byte[length];
                    input.readFully(message, 0, message.length); // read the message

                    System.out.print("The file was downloaded! Specify a name for it:");
                    String nameOfNewFileToSaveInClient = scanner.next();

                    try (FileOutputStream fileOuputStream = new FileOutputStream(dirPathToImportFrom + nameOfNewFileToSaveInClient)) {
                        fileOuputStream.write(message);
                    }
                    System.out.println("File saved on the hard drive!");
                } else {
                    System.out.println(decision);
                }
            } else if (Integer.parseInt(option) == 3) {
                output.writeUTF(option);
                System.out.print(input.readUTF());
                String withIdOrWithName = scanner.next();
                output.writeUTF(withIdOrWithName);

                System.out.print(input.readUTF());
                String IdOrName = scanner.next();
                output.writeUTF(IdOrName);
                System.out.println("The request was sent.");
                System.out.println(input.readUTF());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
