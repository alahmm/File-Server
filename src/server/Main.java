package server;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {
    private static final String address = "192.168.1.29";
    private static final int port = 34522;

    public static String generateString(Random rng, String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        List<FileProperties> list = new ArrayList<>();
        System.out.println("Server started!");
        String dirPath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "server" + File.separator + "data" + File.separator;
        String dirPathToImportFrom = System.getProperty("user.dir") + File.separator + "src" + File.separator + "client" + File.separator + "data" + File.separator;
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
                        output.writeUTF("Enter name of the file:"); // resend it to the client
                        int length = input.readInt();                // read length of incoming message
                        byte[] message = new byte[length];
                        input.readFully(message, 0, message.length); // read the message

                        output.writeUTF("Enter name of the file to be saved on server:"); // resend it to the client
                        String fileName = input.readUTF(); // read a message from the client:the name of file in client/data/

                        if (fileName.equals("")) {
                            Random rand = new Random();
                            fileName = generateString(rand, "anameoftext", 10);
                        }
                        try (FileOutputStream fileOuputStream = new FileOutputStream(dirPath + fileName)){
                            fileOuputStream.write(message);
                        }
                        Random rand = new Random();
                        int rand_int1 = rand.nextInt(100);
                        output.writeUTF("200 " + rand_int1);
                        FileProperties files = new FileProperties();
                        files.setName(fileName);
                        files.setId(rand_int1);


                        FileOutputStream fileOutputStream
                                = new FileOutputStream("data.txt", true);
                        ObjectOutputStream objectOutputStream
                                = new ObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(files);
                        //map.put(files.getId(), files.getName());
                        //objectOutputStream.flush();
                        objectOutputStream.close();

                    } else if (Integer.parseInt(msg) == 1) {
                        String nameOfFileToGet = "";
                        output.writeUTF("Do you want to get the file by name or by id (1 - name, 2 - id):");
                        String option = input.readUTF();
                        if (Integer.parseInt(option) == 2) {
                            output.writeUTF("Enter id:");
                            int id = Integer.parseInt(input.readUTF());
                            List<FileProperties> results = new ArrayList<>();

                            FileInputStream fis = null;
                            try {
                                fis =new FileInputStream("data.txt");
                                while (true) {
                                    ObjectInputStream ois = new ObjectInputStream(fis);
                                    results.add((FileProperties) ois.readObject());
                                }
                            } catch (EOFException ignored) {
                                // as expected
                            } finally {
                                if (fis != null)
                                    fis.close();
                            }
                            boolean isId = false;
                            for (FileProperties object : results
                            ) {
                                if (object.getId() == id) {
                                    isId = true;
                                    nameOfFileToGet = object.getName();
                                    output.writeUTF("200");
                                    Path path = Paths.get(dirPath + nameOfFileToGet);
                                    byte[] message = Files.readAllBytes(path);
                                    output.writeInt(message.length); // write length of the message
                                    output.write(message);// write the message
                                }
                            }
                            if(!isId)
                            {
                                output.writeUTF("The response says that this file is not found!");
                            }
                        } else if (Integer.parseInt(option) == 1)  {
                            output.writeUTF("Enter name:");
                            nameOfFileToGet = input.readUTF();

                            File file = new File(dirPath + nameOfFileToGet);
                            Path path = Paths.get(dirPath + nameOfFileToGet);
                            if (file.exists()) {
                                output.writeUTF("200");
                                byte[] message = Files.readAllBytes(path);
                                output.writeInt(message.length); // write length of the message
                                output.write(message);// write the message
                            } else {
                                output.writeUTF("The response says that this file is not found!");
                            }
                        }
                    } else if (Integer.parseInt(msg) == 3) {
                        String nameOfFileToDelete = "";
                        output.writeUTF("Do you want to delete the file by name or by id (1 - name, 2 - id):");
                        String option = input.readUTF();
                        if (Integer.parseInt(option) == 2) {
                            output.writeUTF("Enter id:");
                            int id = Integer.parseInt(input.readUTF());
                            List<FileProperties> results = new ArrayList<>();

                            FileInputStream fis = null;
                            try {
                                fis =new FileInputStream("data.txt");
                                while (true) {
                                    ObjectInputStream ois = new ObjectInputStream(fis);
                                    results.add((FileProperties) ois.readObject());
                                }
                            } catch (EOFException ignored) {
                                // as expected
                            } finally {
                                if (fis != null)
                                    fis.close();
                            }
                            boolean isId = false;
                            for (FileProperties object : results
                            ) {
                                if (object.getId() == id) {
                                    isId = true;
                                    nameOfFileToDelete = object.getName();
                                    Files.delete(Paths.get(dirPath + nameOfFileToDelete));
                                    output.writeUTF("The response says that this file was deleted successfully!");
                                }
                            }
                            if(!isId)
                            {
                                output.writeUTF("The response says that this file is not found!");
                            }
                        } else if (Integer.parseInt(option) == 1)  {
                            output.writeUTF("Enter name:");
                            nameOfFileToDelete = input.readUTF();
                        }
                        File file = new File(dirPath + nameOfFileToDelete);
                        if (file.exists()) {
                            Files.delete(Paths.get(dirPath + nameOfFileToDelete));
                            output.writeUTF("The response says that this file was deleted successfully!");
                        } else {
                            output.writeUTF("The response says that this file is not found!");
                        }
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}






