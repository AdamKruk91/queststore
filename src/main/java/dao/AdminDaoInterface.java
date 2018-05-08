package dao;

import exceptions.DataAccessException;
import model.AdminModel;

import java.sql.SQLException;

public interface AdminDaoInterface {

    AdminModel getAdmin(int id) throws DataAccessException;
}
