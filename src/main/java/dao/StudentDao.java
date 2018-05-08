package dao;

import exceptions.DataAccessException;
import model.GroupModel;
import model.WalletModel;
import model.StudentModel;

import java.util.List;

public class StudentDao extends ManipulationDao implements StudentDaoInterface {
    private WalletDaoInterface wdao = new WalletDao();
    private GroupDaoInterface gdao = new GroupDao();
    private final int USER_CATEGORY_ID = 1;


    @Override
    public StudentModel getStudentById(int id) throws DataAccessException {
        return null;
    }

    @Override
    public List<StudentModel> getStudentsCollection() throws DataAccessException {
        return null;
    }

    @Override
    public void addStudent(StudentModel student) throws DataAccessException {

    }

    @Override
    public void deleteStudent(int id) throws DataAccessException {

    }

    @Override
    public void updateStudent(StudentModel student) throws DataAccessException {

    }

    @Override
    public void updateWallet(StudentModel student) throws DataAccessException {

    }
}
