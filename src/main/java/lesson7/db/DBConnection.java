package lesson7.db;

import java.sql.*;
import java.util.Arrays;
import java.util.Base64;

public class DBConnection {
    private Connection connection;
    private Statement statement;

    public void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:main.db"); // [protocol]:[subprotocol]:[name]
        statement = connection.createStatement();
        System.out.println("sqlite connected");
    }

    public void disconnect() {
        try {
            statement.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("sqlite disconnected");
    }

    public void createUserTable() {
        try {
            connect();
            statement.execute("CREATE TABLE users (\n" +
                    "id          Integer PRIMARY KEY AUTOINCREMENT,\n" +
                    "login       String,\n" +
                    "password    String,\n" +
                    "nickname    String,\n" +
                    "UNIQUE(nickname)\n" +
                    ");");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            disconnect();
        }
    }

    public void fillUserTable() {
        try {
            connect();
            for (int i = 1; i <= 3; i++) {
                User user = new User(i, "user_" + i, encodePassword("user_" + i), "user_" + i);
                insertUser(user);
            }
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        } finally {
            disconnect();
        }

    }

    private String encodePassword(String password) {
        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    private String decodePassword(String password) {
        return Arrays.toString(Base64.getDecoder().decode(password.getBytes()));
    }

    private void insertUser(User user) {
        try {
            PreparedStatement insertSt = connection.prepareStatement("INSERT INTO users (login, password, nickname) VALUES (?, ?, ?);");
            insertSt.setString(1, user.login);
            insertSt.setString(2, user.password);
            insertSt.setString(3, user.nickname);
            insertSt.executeUpdate();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
    }

    public String selectNicknameByLoginPassword(String login, String password) throws SQLException {
        String nickname = null;
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND password = ?;");
            ps.setString(1, login);
            ps.setString(2, encodePassword(password));
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                nickname = resultSet.getString(1);
            }
            return nickname;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }
        return nickname;
    }

    public boolean changeNickName(String newNickname, String currNickname) {
        try {
            connect();
            PreparedStatement ps = connection.prepareStatement("UPDATE users SET nickname = ? WHERE nickname = ?;");
            ps.setString(1, newNickname);
            ps.setString(2, currNickname);
            int res = ps.executeUpdate();
            System.out.println("Nickname has been changed to: " + newNickname);
            return res > 0;
        } catch (SQLException | ClassNotFoundException sqlException) {
            sqlException.printStackTrace();
        } finally {
            disconnect();
        }
        System.out.println("Nickname " + currNickname + " has not been changed");
        return false;
    }


    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        DBConnection db = new DBConnection();
        db.connect();
        db.createUserTable();
        db.fillUserTable();
        db.disconnect();
    }

    class User {
        private Integer id;
        private String login;
        private String password;
        private String nickname;

        public User(Integer id, String login, String password, String nickname) {
            this.id = id;
            this.login = login;
            this.password = password;
            this.nickname = nickname;
        }

        public Integer getId() {
            return id;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }

        public String getNickname() {
            return nickname;
        }
    }
}
