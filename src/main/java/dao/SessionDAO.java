package dao;

import exceptions.DataAccessException;
import model.Session;

public interface SessionDAO {
    boolean sessionExists(String sessionID) throws DataAccessException;
    void addSession(Session session) throws DataAccessException;
    void deleteSession(int userID) throws DataAccessException;
    Session getSession(int userID) throws DataAccessException;
    Session getSession(String sessionID) throws DataAccessException;
}
