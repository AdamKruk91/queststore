package dao;

import java.util.List;

import exceptions.DataAccessException;
import model.Student;

public interface StudentDAO {

    Student getStudentById(int id) throws DataAccessException;
    List<Student> getStudentsCollection()throws DataAccessException;
    void addStudent(Student student) throws DataAccessException;
    void deleteStudent(Student student) throws DataAccessException;
    void updateStudent(Student student) throws DataAccessException;
    }

