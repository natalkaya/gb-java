package lesson7.server;

import lesson7.db.DBConnection;

public class SQLitePersonInfoService implements PersonInfoService {
    private final DBConnection dbConnection;

    public SQLitePersonInfoService() {
        this.dbConnection = new DBConnection();
    }

    @Override
    public boolean changeNickName(String newNickname, String currNickname) {
        return dbConnection.changeNickName(newNickname, currNickname);
    }
}
