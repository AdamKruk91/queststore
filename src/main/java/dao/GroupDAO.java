package dao;

import exceptions.DataAccessException;
import model.Group;
import model.User;

import java.util.List;

public interface GroupDAO {

    void add(Group group) throws DataAccessException;
    List<Group> getAll() throws DataAccessException;
    void remove(Group group) throws DataAccessException;
    Group getByUser(int id) throws DataAccessException;
    Group getByGroup(int id) throws DataAccessException;
    void addUserToGroup(int ID, Group group) throws DataAccessException;

}
