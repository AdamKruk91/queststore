package dao;

import java.sql.ResultSet;


public class LoginDao extends UserDao {


    public int findStatusId(String login, String password) {
        ResultSet result = selectDataFromTable("Login", "id_status", "email='" + login + "' AND password='" + password + "'");
        return getIntFromResult(result, "id_status");
    }

    public String findStatus(int idStatus) {
        ResultSet result = selectDataFromTable("status", "name", "id_status=" + idStatus);
        return getStringFromResult(result, "name");
    }

    public int findStatusIdByName(String name) {
        ResultSet result = selectDataFromTable("status", "id_status", "name='" + name + "'");
        return getIntFromResult(result, "id_status");
    }

    public int findLoginId(String login, String password) {
        ResultSet result = selectDataFromTable("Login", "id_login", " email='" + login + "' AND password='" + password + "'");
        return getIntFromResult(result, "id_login");
    }

    public void insertNewLogin(String email, String password, int idStatus){
        String loginTable = "Login";
        String loginColumns = "(email, password, id_status)";
        String loginValues = "('"+email+"', '"+ password + "', " + idStatus + ");";
        insertDataIntoTable(loginTable, loginColumns, loginValues);

    }
}