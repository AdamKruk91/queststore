package dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


import model.MentorModel;

public class MentorDao extends ManipulationDao implements MentorDaoInterface {

    private LoginDao loginDao = new LoginDao();

    private int getIdStatus() {
        ResultSet result = selectDataFromTable("Status", "id_status", "name='Mentor'");
        return getIntFromResult(result, "id_status");
    }

    private int insertNewLogin(String email, String password) {
        int idStatus = loginDao.findStatusIdByName("Mentor");
        loginDao.insertNewLogin(email, password, idStatus);
        return loginDao.findLoginId(email, password);
    }

    public void insertNewMentor(MentorModel mentor) {
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
        updateDataInTable("Mentor", "first_name='"+name+"', last_name='"+lastName+"'", "id_mentor=" + idMentor);
    }

    public List<MentorModel> getAllMentorsCollection() {

        List<MentorModel> mentorCollection = new ArrayList<>();
        String columns = "email, password, Mentor.first_name, Mentor.last_name, Mentor.id_mentor, Mentor.id_group";
        String joinStatement = "Mentor.id_login = Login.id_login";
        ResultSet result = selectFromJoinedTables(columns, "Login", "Mentor", joinStatement);
        try {
            while (result.next()) {
                String firstName = result.getString("first_name");
                String lastName = result.getString("last_name");
                String email = result.getString("email");
                String password = result.getString("password");
                int id = result.getInt("id_mentor");
                int idGroup = result.getInt("id_group");
                MentorModel mentor = new MentorModel(id, firstName, lastName, email, password, idGroup);
                mentorCollection.add(mentor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentorCollection;
    }

}


