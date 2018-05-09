package dao;

import exceptions.DataAccessException;

public interface LoginDao {
    int findStatusIdByName(String name) throws DataAccessException;
    String getUserCategory(int userID) throws DataAccessException;
    int getUserId(String login, String password) throws DataAccessException;
}
