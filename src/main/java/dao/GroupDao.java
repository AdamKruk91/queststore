package dao;

import model.GroupModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDao extends ManipulationDao implements GroupDaoInterface {

    public void addNewGroup(String groupName){
        ManipulationDao dao = new ManipulationDao();
        String table = "Groups";
        String columns = "('name')";
        String values = "('"+groupName+"')";
        dao.insertDataIntoTable(table, columns, values);
    }

    private ResultSet createGroupsResult() {
        String sql = "SELECT * FROM Groups";
        ResultSet result = executeSelect(sql);
        return result;
    }

    public List<GroupModel> getGroupsCollection() {
        ResultSet result =  createGroupsResult();
        List<GroupModel> groupCollection = new ArrayList<>();
        try {
            while (result.next()) {
                int id = result.getInt("id_group");
                String name = result.getString("name");
                GroupModel group = new GroupModel(id, name);
                groupCollection.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupCollection;
    }

    public String getGroupNameById(int groupId){
        ResultSet result = selectDataFromTable("Groups","name","id_group ="+groupId);
        String groupName = getStringFromResult(result,"name");
        return groupName;
    }
}
