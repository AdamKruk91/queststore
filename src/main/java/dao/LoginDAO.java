package dao;

import exceptions.DataAccessException;

public interface LoginDAO {

    String getUserCategory(int userID) throws DataAccessException;
    int getUserId(String login, String password) throws DataAccessException;
}
