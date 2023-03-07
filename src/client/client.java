package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class client {
    private static final String SERVER_ADDRESS = "192.168.1.29";//you can also set adress of other computer if the server is in another machine
    private static final int SERVER_PORT = 34522;

    public static void main(String[] args) {
        System.out.println("Client started!");
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
            System.out.println("Sent: Give me everything you have!");
            String receivedMsg = input.readUTF(); // read the reply from the server*/

            /**
             * to send 5 messages
             */
            String msg = "hello";
            output.writeUTF(msg);
            System.out.println("Sent: Give me everything you have!");
            String receivedMsg2 = input.readUTF();
            System.out.println("Received: All files were sent!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
