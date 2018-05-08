package dao;

import exceptions.DataAccessException;
import model.GroupModel;
import model.MentorModel;
import model.StudentModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GroupDao extends ManipulationDao implements GroupDaoInterface {

    private MentorDaoInterface mentorDAO = new MentorDao();
    private StudentDaoInterface studentDao = new StudentDao();
    private final int CODECOOLER_CATEGORY_ID = 1;
    private final int MENTOR_CATEGORY_ID = 2;

    public void addNewGroup(GroupModel group) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "INSERT INTO 'group' (name) VALUES (?);");
            ps.setString(1, group.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Adding new group failed!");
        }
    }


    public List<GroupModel> getGroupsCollection() throws DataAccessException {
        try {
            PreparedStatement ps = null;
                ps = getConnection().prepareStatement("SELECT id FROM 'group';");

            ResultSet rs = ps.executeQuery();

            ArrayList<GroupModel> groups = new ArrayList<>();
            while (rs.next()) {
                int groupID = rs.getInt("id");
                GroupModel group = getByID(groupID);
                groups.add(group);
            }
            return groups;
        } catch (SQLException e) {
            throw new DataAccessException("Getting groups collection failed");
        }
    }


    public void removeGroup(GroupModel group) throws DataAccessException {
        try {
            PreparedStatement ps = null;
            ps = getConnection().prepareStatement("DELETE FROM 'group' WHERE id=  ?;");
            ps.setInt(1, group.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Removing group failed");
        }
    }

    private GroupModel getByID(int id) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement(
                "SELECT DISTINCT user_id, user_category_id, 'group'.name as group_name FROM 'group' " +
                        "  LEFT JOIN 'user_group' ON 'group'.id = 'user_group'.group_id" +
                        "    LEFT JOIN 'user' ON user.id = user_group.user_id" +
                        "      WHERE 'group'.id = ?;");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        ArrayList<StudentModel> students = new ArrayList<>();
        ArrayList<MentorModel> mentors = new ArrayList<MentorModel>();

        String groupName = null;
        while(rs.next()) {
            if (groupName == null) {
                groupName = rs.getString("group_name");
            }
            int userID = rs.getInt("user_id");
            int userCategoryID = rs.getInt("user_category_id");
            switch(userCategoryID) {
                case CODECOOLER_CATEGORY_ID:
                    students.add(studentDao.getByID(userID));
                    break;
                case MENTOR_CATEGORY_ID:
                    mentors.add(mentorDAO.getByID(userID));
                    break;
            }
        }
        return new GroupModel(id, groupName, students, mentors);
    }

}