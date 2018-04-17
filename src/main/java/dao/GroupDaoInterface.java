package dao;

import model.GroupModel;

import java.util.List;

public interface GroupDaoInterface {

    void addNewGroup(String groupName);
    List<GroupModel> getGroupsCollection();
    String getGroupNameById(int groupId);
}
