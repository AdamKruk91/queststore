package dao;

import exceptions.DataAccessException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginDAOSQL extends ManipulationDAOSQL implements LoginDAO {

    public String getUserCategory(int userID) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT user_category.name FROM user_category " +
                    "  INNER JOIN user ON user_category.id = user.id " +
                    "    WHERE user.id = ?;");
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            return rs.getString("user_category.name");
        } catch (SQLException e) {
            throw new DataAccessException("Login error: getUserId failed");
        }
    }

    public int getUserId(String login, String password) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT id FROM user " +
                            "    WHERE login = ? AND password = ?;");
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            return rs.getInt("id");
        } catch (SQLException e) {
            throw new DataAccessException("Login error: getUserId failed");
        }
    }

}
