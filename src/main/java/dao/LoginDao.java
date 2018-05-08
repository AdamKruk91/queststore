package dao;

import model.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDao extends ManipulationDao {


    public int findStatusId(String login, String password) throws SQLException {
        ResultSet result = selectDataFromTable("Login", "id_status", "email='" + login + "' AND password='" + password + "'");
        return getIntFromResult(result, "id_status");
    }

    public String findStatus(int idStatus) {
        ResultSet result = selectDataFromTable("status", "name", "id_status=" + idStatus);
        return getStringFromResult(result, "name");
    }

    public int findStatusIdByName(String name) throws SQLException {
        ResultSet result = selectDataFromTable("status", "id_status", "name='" + name + "'");
        return getIntFromResult(result, "id_status");
    }

    public String getUserCategory(int userID) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement(
                "SELECT user_category.name FROM user_category " +
                "  INNER JOIN user ON user_category.id = user.id " +
                "    WHERE user.id = ?;");
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        return rs.getString("user_category.name");
    }

    public int getUserId(String login, String password) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement(
                "SELECT id FROM user " +
                        "    WHERE login = ? AND password = ?;");
        ps.setString(1, login);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs.getInt("id");
    }

    public void insertNewLogin(String email, String password, int idStatus){
        String loginTable = "Login";
        String loginColumns = "(email, password, id_status)";
        String loginValues = "('"+email+"', '"+ password + "', " + idStatus + ");";
        insertDataIntoTable(loginTable, loginColumns, loginValues);
    }

    public void updateLoginTable(UserModel user, String email, String password) throws SQLException {
        String newEmail = user.getEmail();
        String newPassword = user.getPassword();
        int idLogin = getUserId(email, password);
        updateDataInTable("Login", "email='"+newEmail+"', password='"+newPassword+"'", "id_login="+idLogin);
    }

    public void removeLoginByMail(String email){
        String condition = "Login.email = " + "'" + email + "'";
        removeDataFromTable("Login", condition);
    }
}
