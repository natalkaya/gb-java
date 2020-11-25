package lesson7.server;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClientHandler implements LogRunner {
    DataInputStream in;
    DataOutputStream out;
    Server server;
    Socket socket;
    FileWriter outputStream;

    private String nickname;
    private String login;
    private String historyFileName;
    private static Logger log;

    private String historyFileTemplate(String nickname) {
        return String.format("history_%s.txt", nickname);
    }

    private String loadMessages() {
        String lastMsgs = null;
        try {
            List<String> fileStrs = Files.readAllLines(Paths.get(historyFileTemplate(nickname)));
            lastMsgs = fileStrs.stream()
                    .limit(100)
                    .map(String::valueOf)
                    .collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lastMsgs;
    }

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            log = initLogger(ClientHandler.class.getName());
            log.info("Client connected " + socket.getRemoteSocketAddress());

            new Thread(() -> {
                try {
                    //цикл аутентификации
                    while (true) {
                        String str = in.readUTF();

                        if (str.startsWith("/auth ")) {
                            String[] token = str.split("\\s");
                            if (token.length < 3) {
                                continue;
                            }
                            String newNick = server.getAuthService().getNicknameByLoginAndPassword(token[1], token[2]);
                            if (newNick != null) {
                                nickname = newNick;
                                server.subscribe(this);
                                sendMsg("/authok " + newNick);

                                this.historyFileName = historyFileTemplate(nickname);
                                final File file = new File(historyFileName);
                                if (file.exists()) {
                                    sendMsg(loadMessages());
                                    this.outputStream = new FileWriter(file);
                                } else {
                                    if (file.createNewFile()) {
                                        this.outputStream = new FileWriter(file, true);
                                    } else {
                                        log.info("Cannot create file with name: " + historyFileName);
                                    }
                                }
                                break;
                            } else {
                                sendMsg("Неверный логин / пароль");
                            }
                        }
                    }
                    //цикл работы

                    while (true) {
                        String str = in.readUTF();

                        if (str.equals("/end")) {
                            sendMsg("/end");
                            break;
                        }

                        if (str.startsWith("/changeNick")) {
                            log.info("/changeNick pressed");
                            server.changeNickname(this, str);
                        }

                        if (str.startsWith("/w ")) {
                            server.privateMsg(this, str);
                        } else {
                            server.broadcastMsg(this, str);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    server.unsubscribe(this);
                    log.info("Client disconnected " + socket.getRemoteSocketAddress());
                    try {
                        socket.close();
                        in.close();
                        out.close();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
