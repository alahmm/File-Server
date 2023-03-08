package server;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String address = "192.168.1.29";
    private static final int port = 34522;
    public static void main(String[] args) {
        System.out.println("Server started!");
        String dirPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "server" + File.separator + "data" + File.separator;
        //String dirPath = "C:\\Users\\alahmm\\IdeaProjects\\File Server1\\File Server\\task\\src\\server\\data\\";
        try (ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));) {//try-with-resources instead of using close
            while (true) {
                try (
                        Socket socket = server.accept(); // accept a new client
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String msg = input.readUTF(); // read a message from the client
                    if (msg.equals("exit")) {
                        return;
                    } else if (Integer.parseInt(msg) == 2) {
                        output.writeUTF("Enter filename:"); // resend it to the client
                        String fileName = input.readUTF(); // read a message from the client
                        File file = new File(dirPath + fileName);
                        if(!file.exists()) {
                            output.writeUTF("200");
                            output.writeUTF("Enter file content:"); // resend it to the client
                            String fileContent = input.readUTF(); // read a message from the client
                            try (PrintWriter printWriter = new PrintWriter(file)) {
                                printWriter.print(fileContent);
                            }
                            output.writeUTF("The response says that file was created!");
                        } else {
                            output.writeUTF("403");
                        }
                    } else if (Integer.parseInt(msg) == 1) {
                        output.writeUTF("Enter filename:"); // resend it to the client
                        String fileName = input.readUTF(); // read a message from the client
                        File file = new File(dirPath + fileName);
                        try {
                            Scanner scanner = new Scanner(file);
                            while (scanner.hasNext()) {
                                output.writeUTF("200");
                                String content = scanner.nextLine();
                                output.writeUTF(content);
                            }
                            scanner.close();
                        } catch (FileNotFoundException e) {
                            output.writeUTF("404");
                        }
                    } else if (Integer.parseInt(msg) == 3) {
                        output.writeUTF("Enter filename:"); // resend it to the client
                        String fileName = input.readUTF(); // read a message from the client
                        File file = new File(dirPath + fileName);
                        if(file.exists()) {
                            output.writeUTF("200");
                            //file.delete();

                            Files.delete(Paths.get(file.getAbsolutePath()));
/*                            Files.deleteIfExists(
                                    Paths.get(dirPath + fileName));*/
                            if (!file.exists()) {
                                output.writeUTF("The response says that the file was successfully deleted!"); // resend it to the client
                            }
                        } else {
                            output.writeUTF("403");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}






