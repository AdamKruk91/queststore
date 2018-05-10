package dao;

import exceptions.DataAccessException;
import model.IteratorImpl;
import model.Level;

import java.sql.SQLException;

public interface LevelDAO {
    void add(String name, int exp) throws DataAccessException;
    void remove(int id) throws DataAccessException;
    IteratorImpl getLevels() throws DataAccessException;
    Level getLevel(int totalExp) throws DataAccessException;
}