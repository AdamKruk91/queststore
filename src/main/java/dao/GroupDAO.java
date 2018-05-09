package dao;

import exceptions.DataAccessException;
import model.Group;
import java.util.List;

public interface GroupDAO {

    void add(Group group) throws DataAccessException;
    List<Group> getAll() throws DataAccessException;
    void remove(Group group) throws DataAccessException;
    Group getByID(int id) throws DataAccessException;
}
