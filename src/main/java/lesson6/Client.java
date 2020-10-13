package lesson6;

import java.io.*;
import java.net.Socket;

public class Client {
    final String IP_ADDRESS = "localhost";
    final int PORT = 8189;
    Socket socket = null;
    DataInputStream in;
    DataOutputStream out;
    BufferedReader buffer;
    private final Object mon = new Object();

    public Client() {
        try {
            this.socket = new Socket(IP_ADDRESS, PORT);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            synchronized (mon) {
                new Thread(() -> {
                    try {
                        buffer = new BufferedReader(new InputStreamReader(System.in));
                        while (true) {
                            String in = buffer.readLine();

                            if (in.equals("/end")) {
                                break;
                            }

                            System.out.println("Client: " + in);
                            out.writeUTF(in);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            synchronized (mon) {
                new Thread(() -> {
                    while (true) {
                        try {
                            String fromServer = in.readUTF();
                            if (in.equals("/end")) {
                                break;
                            }
                            System.out.println("Server: " + fromServer);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        Client client = new Client();
    }
}
