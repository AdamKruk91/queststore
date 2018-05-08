package dao;

import exceptions.DataAccessException;
import model.GroupModel;
import model.WalletModel;
import model.StudentModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class StudentDao extends ManipulationDao implements StudentDaoInterface {
    private WalletDaoInterface wdao = new WalletDao();
    private GroupDaoInterface gdao = new GroupDao();
    private final int USER_CATEGORY_ID = 1;


    @Override
    public StudentModel getStudentById(int id) throws DataAccessException {
        try{

        }catch (SQLException e) {
            throw new DataAccessException("Get student failed!");
        }
    }

    @Override
    public List<StudentModel> getStudentsCollection() throws DataAccessException {
        try{

        }catch (SQLException e) {
            throw new DataAccessException("Getting student collection failed!");
        }
    }

    @Override
    public void addStudent(StudentModel student) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "INSERT INTO user (login, password, name, surname, email, user_category_id) " +
                            "VALUES(?, ?, ?, ?, ?, ?)");
            ps.setString(1, student.getLogin());
            ps.setString(2, student.getPassword());
            ps.setString(3, student.getName());
            ps.setString(4, student.getSurname());
            ps.setString(5, student.getEmail());
            ps.setInt(6, USER_CATEGORY_ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Add student failed!");
        }
    }
    @Override
    public void deleteStudent(StudentModel student) throws DataAccessException {
        try{
            PreparedStatement ps = getConnection().prepareStatement("DELETE FROM user WHERE id = ? AND user_category_id=?;");
            ps.setInt(1, student.getID());
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Remove student failed!");
        }
    }

    @Override
    public void updateStudent(StudentModel student) throws DataAccessException {
        try{

        }catch (SQLException e) {
            throw new DataAccessException("Update student failed!");
        }
    }

    @Override
    public void updateWallet(StudentModel student) throws DataAccessException {

    }
}
