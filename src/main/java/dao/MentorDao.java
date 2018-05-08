package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import model.MentorModel;
import exceptions.DataAccessException;

public class MentorDao extends ManipulationDao implements MentorDaoInterface {

    private LoginDao loginDao = new LoginDao();

//    private int getIdStatus() throws SQLException {
//        ResultSet result = selectDataFromTable("Status", "id_status", "name='Mentor'");
//        return getIntFromResult(result, "id_status");
//    }

    private int insertNewLogin(String email, String password) throws SQLException {
        int idStatus = loginDao.findStatusIdByName("Mentor");
        loginDao.insertNewLogin(email, password, idStatus);
        return loginDao.getUserId(email, password);
    }

    public void insertNewMentor(MentorModel mentor) throws SQLException {
        int idLogin = insertNewLogin(mentor.getEmail(), mentor.getPassword());
        String table = "Mentor";
        String columns = "(first_name, last_name, id_login, id_status, id_group)";
        int idStatus = getIdStatus();
        String values = "('" + mentor.getFirstName() + "', '" + mentor.getLastName() + "', " + idLogin +", "+ idStatus + ", " + mentor.getIdGroup() + ");";
        insertDataIntoTable(table, columns, values);
    }

    public void updateMentorTable(MentorModel mentor) {
        String name = mentor.getFirstName();
        String lastName = mentor.getLastName();
        int idMentor = mentor.getID();
        int groupId = mentor.getIdGroup();
        updateDataInTable("Mentor", "first_name='"+name+"', last_name='"+lastName+"'" + ", id_group='"+groupId+"'", "id_mentor=" + idMentor);
    }

    public List<MentorModel> getAllMentors() throws DataAccessException {

        List<MentorModel> mentors = new ArrayList<>();

        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT * FROM user WHERE user_category_id=2;");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                mentors.add(createMentorFrom(ResultSet rs));
            }
            return mentors;

        } catch (SQLException e) {
            throw new DataAccessException("Delete mentor error!");
        }
    }

    @Override
    public void deleteMentor(int id) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "DELETE FROM user WHERE id=?;");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Delete user error!");
        }
    }

    @Override
    public MentorModel getMentor(int id) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT * FROM user WHERE id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return createMentorFrom(ResultSet rs);
        } catch (SQLException e) {
            throw new DataAccessException("Delete mentor error!");
        }
    }

    public MentorModel createMentorFrom(ResultSet rs) throws DataAccessException {
        try {
            int id = rs.getInt("id");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String email = rs.getString("email");
            // TODO: create groupList
            return new MentorModel(id, login, password, name, surname, email, null);
        } catch (SQLException e) {
            throw new DataAccessException("Create mentor error!");
        }
    }
}


