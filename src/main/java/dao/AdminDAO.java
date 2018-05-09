package dao;

import exceptions.DataAccessException;
import model.Admin;

public interface AdminDAO {

    Admin getAdmin(int id) throws DataAccessException;
}
