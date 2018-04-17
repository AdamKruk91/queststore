package dao;

import java.sql.ResultSet;

public interface UserDaoInterface {
    ResultSet selectDataFromTable(String tableName, String columns, String condition);
    ResultSet selectFromJoinedTables(String columns, String tableName, String joinTable, String joinStatement);
    void insertDataIntoTable(String tableName, String columns, String values);
    void updateDataInTable(String table, String setQuerry, String condition);
//    int findStatusId(String login, String password);
//    String findStatus(int idStatus);
//    int findStatusIdByName(String name);
//    int findLoginId(String login, String password);
//    void insertNewLogin(String email, String password, int idStatus);
}
