package dao;

import model.StudentModel;

import java.sql.SQLException;
import java.util.List;

public interface StudentDaoInterface {

    StudentModel getStudentByIdLogin(int idLogin);

    List<StudentModel> getStudentsCollection();

    void insertNewStudent(StudentModel student) throws SQLException;

    void updateWallet(StudentModel student);
}

