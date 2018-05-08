package dao;

import exceptions.DataAccessException;
import model.GroupModel;
import java.util.List;

public interface GroupDaoInterface {

    void addNewGroup(GroupModel group) throws DataAccessException;
    List<GroupModel> getGroupsCollection() throws DataAccessException;
    void removeGroup(GroupModel group) throws DataAccessException;
    GroupModel getByID(int id) throws DataAccessException;
}
