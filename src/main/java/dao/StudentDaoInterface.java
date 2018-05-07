package dao;

import model.StudentModel;

import java.sql.SQLException;
import java.util.List;

public interface StudentDaoInterface {

    StudentModel getStudentById(int id);
    List<StudentModel> getStudentsCollection();
    void addStudent(StudentModel student) throws SQLException;
    void deleteStudent(int id) throws SQLException;
    void updateStudent(StudentModel student) throws SQLException;
    void updateWallet(StudentModel student);
}

