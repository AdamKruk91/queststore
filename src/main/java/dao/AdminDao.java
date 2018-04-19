package dao;

import model.AdminModel;
import model.GroupModel;
import model.StudentModel;
import model.WalletModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao extends ManipulationDao{

    public AdminModel getAdminByIdLogin(int idLogin) {
        String columns = "Login.email, Login.password, Admin.id_admin, first_name, last_name";
        String joinStmt1 = "Login.id_login=Admin.id_login";
        String condition = "Admin.id_login=" +idLogin;

        String sql = "SELECT " + columns + " FROM Admin JOIN Login ON " +  joinStmt1 +
                     " WHERE " +  condition;
        ResultSet result = executeSelect(sql);
        return createAdminObject(result);
    }

    private AdminModel createAdminObject(ResultSet result) {
        AdminModel admin = null;
        try {
            String email = result.getString("email");
            String password = result.getString("password");
            int id = result.getInt("id_admin");
            String firstName = result.getString("first_name");
            String lastName = result.getString("last_name");
            admin = new AdminModel(id, firstName, lastName, email, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return admin;
    }
}
