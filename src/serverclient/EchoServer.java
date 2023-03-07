package serverclient;

import java.io.*;
import java.net.*;

public class EchoServer {
    private static final int PORT = 34522;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {//try-with-resources instead of using close
            while (true) {
                try (
                        Socket socket = server.accept(); // accept a new client
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    /**
                     * to accept one message
                     */
/*                    String msg = input.readUTF(); // read a message from the client
                    //If you need to send or receive something like movies or audio files, you may work directly with bytes rather than strings
                    output.writeUTF(msg); // resend it to the client*/
                    /**
                     * to accept 5 messages
                     */
                    for (int i = 0; i < 5; i++) {
                        String msg = input.readUTF(); // read the next client message
                        output.writeUTF(msg); // resend it to the client
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
