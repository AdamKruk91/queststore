package dao;

import java.util.List;

import exceptions.DataAccessException;
import model.Student;

public interface StudentDAO {
    Student get(int id) throws DataAccessException;
    List<Student> getAll()throws DataAccessException;
    void add(Student student) throws DataAccessException;
    void delete(Student student) throws DataAccessException;
    void update(Student student) throws DataAccessException;
    }

