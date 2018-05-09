package dao;

import model.IteratorImpl;
import model.Level;

import java.sql.SQLException;

public interface LevelDAO {
    void addLevel(String name, int exp) throws SQLException;
    void deleteLevel(int id) throws SQLException;
    IteratorImpl getLevels() throws SQLException;
    Level getLevel(int totalExp) throws SQLException;
}