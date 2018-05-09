package dao;

import exceptions.DataAccessException;
import model.Group;
import java.util.List;

public interface GroupDAO {

    void addNewGroup(Group group) throws DataAccessException;
    List<Group> getGroupsCollection() throws DataAccessException;
    void removeGroup(Group group) throws DataAccessException;
    Group getByID(int id) throws DataAccessException;
}
