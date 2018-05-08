package dao;

import java.util.List;

import exceptions.DataAccessException;
import model.StudentModel;

public interface StudentDaoInterface {

    StudentModel getStudentById(int id) throws DataAccessException;
    List<StudentModel> getStudentsCollection()throws DataAccessException;
    void addStudent(StudentModel student) throws DataAccessException;
    void deleteStudent(int id) throws DataAccessException;
    void updateStudent(StudentModel student) throws DataAccessException;
    void updateWallet(StudentModel student) throws DataAccessException;
}

