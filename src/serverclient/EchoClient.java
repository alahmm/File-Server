package serverclient;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {
    private static final String SERVER_ADDRESS = "192.168.1.29";//you can also set adress of other computer if the server is in another machine
    private static final int SERVER_PORT = 34522;

    public static void main(String[] args) {
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {
            /**
             * to send one message
             */
/*            Scanner scanner = new Scanner(System.in);
            String msg = scanner.nextLine();

            output.writeUTF(msg); // send a message to the server
            String receivedMsg = input.readUTF(); // read the reply from the server

            System.out.println("Received from the server: " + receivedMsg);*/
            /**
             * to send 5 messages
             */
            for (int i = 0; i < 5; i++) {
                Scanner scanner = new Scanner(System.in);
                String msg = scanner.nextLine();

                output.writeUTF(msg);
                String receivedMsg = input.readUTF();

                System.out.println("Received from the server: " + receivedMsg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
