package lesson7.server.authservice;

import lesson7.db.DBConnection;
import lesson7.server.AuthService;

import java.sql.SQLException;

public class SQLiteAuthService implements AuthService {
    private final DBConnection dbConnection;

    public SQLiteAuthService() {
        this.dbConnection = new DBConnection();
        dbConnection.createUserTable();
        dbConnection.fillUserTable();
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {
        String nickname = null;
        try {
            dbConnection.connect();
            nickname = dbConnection.selectNicknameByLoginPassword(login, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            dbConnection.disconnect();
        }
        return nickname;
    }
}
