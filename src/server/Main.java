package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String address = "192.168.1.29";
    private static final int port = 34522;
    public static void main(String[] args) {
        List<String> initialArray = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            initialArray.add("file" + i);
        }
        String[] operations = new String[]{"add", "get", "delete", "exit"};
        List<String> serverStorage = new ArrayList<>();
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));) {//try-with-resources instead of using close
            while (true) {
                try (
                        Socket socket = server.accept(); // accept a new client
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    System.out.println("Received: Give me everything you have!");
                    /**
                     * to accept one message
                     */
                    String msg = input.readUTF(); // read a message from the client
/*                    System.out.println(msg);
                    //System.out.println("Received: Give me everything you have!");
                    String[] listOfInput = msg.split("\\s+");
                    if (listOfInput[0].equals(operations[0])) {
                        if (initialArray.contains(listOfInput[1]) && !serverStorage.contains(listOfInput[1])) {
                            serverStorage.add(listOfInput[1]);
                            System.out.println(listOfInput[1]);
                            output.writeUTF(msg); // resend it to the client
                        }
                    } else if (listOfInput[0].equals(operations[1])) {
                        if (serverStorage.contains(listOfInput[1])) {
                            System.out.printf(listOfInput[1]);
                            output.writeUTF(msg); // resend it to the client
                        }
                    } else if (listOfInput[0].equals(operations[2])) {
                        if (serverStorage.contains(listOfInput[1])) {
                            serverStorage.remove(listOfInput[1]);
                            System.out.println(listOfInput[1]);
                            output.writeUTF(msg); // resend it to the client
                        }
                    }*/

                    output.writeUTF(msg); // resend it to the client
                    System.out.println("Sent: All files were sent!");
                    return;
                    /**
                     * to accept 5 messages
                     */
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}






