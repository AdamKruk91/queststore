package dao;

import exceptions.DataAccessException;
import model.Group;
import model.Mentor;
import model.Student;
import model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GroupDAOSQL extends ManipulationDAOSQL implements GroupDAO {

//    private MentorDAO mentorDAO = new MentorDAOSQL();
    private final int CODECOOLER_CATEGORY_ID = 1;
    private final int MENTOR_CATEGORY_ID = 2;

    public void add(Group group) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "INSERT INTO 'group' (name) VALUES (?);");
            ps.setString(1, group.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Adding new group failed!");
        }
    }

    public void addUserToGroup(User user, Group group) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement("INSERT INTO user_group (user_id, group_id) VALUES(?, ?);");
            ps.setInt(1, user.getID());
            ps.setInt(2, group.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Add user to group failed");
        }
    }

    public List<Group> getAll() throws DataAccessException {
        try {
            PreparedStatement ps = null;
                ps = getConnection().prepareStatement("SELECT id FROM 'group';");

            ResultSet rs = ps.executeQuery();

            ArrayList<Group> groups = new ArrayList<>();
            while (rs.next()) {
                int groupID = rs.getInt("id");
                Group group = getByGroup(groupID);
                groups.add(group);
            }
            return groups;
        } catch (SQLException e) {
            throw new DataAccessException("Getting groups collection failed");
        }
    }


    public void remove(Group group) throws DataAccessException {
        try {
            PreparedStatement ps = null;
            ps = getConnection().prepareStatement("DELETE FROM 'group' WHERE id=  ?;");
            ps.setInt(1, group.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Removing group failed");
        }
    }

    public Group getByGroup(int id) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT DISTINCT user_id, user_category_id, 'group'.id as 'groupID', 'group'.name as group_name FROM 'group' " +
                            "  LEFT JOIN 'user_group' ON 'group'.id = 'user_group'.group_id" +
                            "    LEFT JOIN 'user' ON user.id = user_group.user_id" +
                            "      WHERE 'group'.id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            ArrayList<Student> students = new ArrayList<>();
            ArrayList<Mentor> mentors = new ArrayList<Mentor>();
            String groupName = null;
            int groupID = 0;
            while (rs.next()) {
                if(groupName == null) {
                    groupName = rs.getString("group_name");
                }
                int userID = rs.getInt("user_id");
                if(groupID == 0) {
                    groupID = rs.getInt("groupID");
                }
                int userCategoryID = rs.getInt("user_category_id");
                switch (userCategoryID) {
                    case CODECOOLER_CATEGORY_ID:
                        StudentDAO studentDAO = new StudentDAOSQL();
                        students.add(studentDAO.get(userID));
                        break;
                    case MENTOR_CATEGORY_ID:
                        MentorDAO mentorDAO = new MentorDAOSQL();
                        mentors.add(mentorDAO.get(userID));
                        break;

                }
            }
            return new Group(groupID, groupName, students, mentors);
        } catch (SQLException e) {
            throw new DataAccessException("Group getById failed!");
        }
    }

    public Group getByUser(int id) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT DISTINCT 'group'.id as 'groupID' FROM 'group' " +
                            "  LEFT JOIN 'user_group' ON 'group'.id = 'user_group'.group_id" +
                            "    LEFT JOIN 'user' ON user.id = user_group.user_id" +
                            "      WHERE 'user'.id = ?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return getByGroup(rs.getInt("groupID"));
        } catch (SQLException e) {
            throw new DataAccessException("Group getById failed!");
        }
    }

}