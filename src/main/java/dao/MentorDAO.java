package dao;

import java.util.List;

import exceptions.DataAccessException;
import model.Mentor;

public interface MentorDAO {

    void add(Mentor mentor) throws DataAccessException;
    void update(Mentor mentor) throws DataAccessException;
    List<Mentor> getAll() throws DataAccessException;
    void delete(int id) throws DataAccessException;
    Mentor get(int id) throws DataAccessException;
    int getByLogin(String login) throws DataAccessException;
}
