package lesson7.server;

import lesson7.server.authservice.SQLiteAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server implements LogRunner {
    private List<ClientHandler> clients;
    private AuthService authService;
    private PersonInfoService personInfoService;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private static Logger log;

    public Server() {
        clients = new CopyOnWriteArrayList<>();
//        authService = new SimpleAuthService();
        authService = new SQLiteAuthService();
        personInfoService = new SQLitePersonInfoService();
        ServerSocket server = null;
        Socket socket = null;
        final int PORT = 8189;
        log = initLogger(Server.class.getName());

        try {
            server = new ServerSocket(PORT);
            log.info("Server started");

            while (true) {
                socket = server.accept();
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdownNow();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(ClientHandler sender, String msg) {
        String message = String.format("[ %s ]: %s", sender.getNickname(), msg);
        executorService.submit(() -> {
            for (ClientHandler c : clients) {
                c.sendMsg(message);
                saveMessage(c, message);
            }
        });
    }

    public void privateMsg(ClientHandler sender, String msg) {
        executorService.submit(() -> {
            if (msg.startsWith("/w ")) {
                String[] arr = msg.trim().split(" ");
                String recipientNick = arr[1];
                String message = String.format("[ %s ]: %s", sender.getNickname(), arr[2]);

                Optional<ClientHandler> recipient = clients.stream()
                        .filter(c -> c.getNickname().equals(recipientNick))
                        .findFirst();

                if (recipient.isPresent()) {
                    sender.sendMsg(message);
                    recipient.get().sendMsg(message);
                    saveMessage(sender, message);
                } else {
                    sender.sendMsg("User is offline / not found");
                }
            }
        });
    }

    public void saveMessage(ClientHandler sender, String msg) {
        try {
            sender.outputStream.append(msg).append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeNickname(ClientHandler sender, String msg) {
        System.out.println("/changeNick pressed: ");
        if (msg.startsWith("/changeNick")) {
            String[] arr = msg.trim().split(" ");
            String newNickname = arr[1];
            boolean isChanged = personInfoService.changeNickName(newNickname, sender.getNickname());
            if (isChanged) sender.setNickname(newNickname);
            broadcastMsg(sender, "User has changes nickname, current nickname: " + newNickname);
        }
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }

    public AuthService getAuthService() {
        return authService;
    }
}
