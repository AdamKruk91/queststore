package dao;

import model.Iterator;
import model.Level;

import java.sql.SQLException;

public interface LevelDaoInterface {
    void addLevel(String name, int exp) throws SQLException;
    void deleteLevel(int id) throws SQLException;
    Iterator getLevels() throws SQLException;
    Level getLevel(int totalExp) throws SQLException;
}