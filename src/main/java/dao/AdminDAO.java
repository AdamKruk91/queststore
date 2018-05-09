package dao;

import exceptions.DataAccessException;
import model.Admin;

public interface AdminDAO {

    Admin get(int id) throws DataAccessException;
}
