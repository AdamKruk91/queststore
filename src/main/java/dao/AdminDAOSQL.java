package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.DataAccessException;
import model.Admin;

public class AdminDAOSQL extends ManipulationDAOSQL implements AdminDAO {

    public Admin get(int id) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    " SELECT id, login, password, name, surname, email FROM user WHERE id  = ?;");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            return createFrom(result);
        } catch (SQLException e){
            throw new DataAccessException("Create admin object error");
        }
    }

    private Admin createFrom(ResultSet result) throws SQLException {
        Admin admin;
        int id = result.getInt("id");
        String login = result.getString("login");
        String password = result.getString("password");
        String name = result.getString("name");
        String surname = result.getString("surname");
        String email = result.getString("email");
        admin = new Admin(id, login, password, name, surname, email);
        return admin;
    }
}
