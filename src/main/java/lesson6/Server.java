package lesson6;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    DataInputStream in;
    DataOutputStream out;
    BufferedReader buffer;
    private final Object mon = new Object();

    public Server() {
        ServerSocket server = null;
        Socket socket = null;
        final int PORT = 8189;

        try {
            server = new ServerSocket(PORT);
            System.out.println("Server started");
            socket = server.accept();
            System.out.println("Client connected");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            buffer = new BufferedReader(new InputStreamReader(System.in));

            synchronized (mon) {
                new Thread(() -> {
                    try {
                        while (true) {
                            String str = in.readUTF();

                            if (str.equals("/end")) {
                                break;
                            }
                            System.out.println("Client: " + str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            synchronized (mon) {
                new Thread(() -> {
                    try {
                        while (true) {
                            String bufferStr = buffer.readLine();
                            if (bufferStr.equals("/end")) {
                                break;
                            }
                            System.out.println("Server: " + bufferStr);
                            out.writeUTF(bufferStr);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //fixme: don't understand when need to try closing a socket
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }
}
