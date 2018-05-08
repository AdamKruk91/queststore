package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.DataAccessException;
import model.AdminModel;

public class AdminDao extends ManipulationDao implements AdminDaoInterface{

    public AdminModel getAdmin(int id) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    " SELECT id, login, password, name, surname, email FROM user WHERE id  = ?;");
            ps.setInt(1, id);
            ResultSet result = ps.executeQuery();
            return createAdminObject(result);
        } catch (SQLException e){
            throw new DataAccessException("Create admin object error");
        }
    }

    private AdminModel createAdminObject(ResultSet result) throws SQLException {
        AdminModel admin;
        int id = result.getInt("id");
        String login = result.getString("login");
        String password = result.getString("password");
        String name = result.getString("name");
        String surname = result.getString("surname");
        String email = result.getString("email");
        admin = new AdminModel(id, login, password, name, surname, email);
        return admin;
    }
}
