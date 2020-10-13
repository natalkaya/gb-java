package lesson7.server;

public interface AuthService {
    String getNicknameByLoginAndPassword(String login, String password);
}
