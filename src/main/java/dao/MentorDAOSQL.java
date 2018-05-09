package dao;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import model.Mentor;
import exceptions.DataAccessException;

public class MentorDAOSQL extends ManipulationDAOSQL implements MentorDAO {

    @Override
    public void add(Mentor mentor) throws DataAccessException {
        final int CATEGORY_ID = 2;
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "INSERT INTO user (login, password, name, surname, email, user_category_id) " +
                            "  VALUES (?, ?, ?, ?, ?, ?);");
            ps.setString(1, mentor.getLogin());
            ps.setString(2, mentor.getPassword());
            ps.setString(3, mentor.getName());
            ps.setString(4, mentor.getSurname());
            ps.setString(5, mentor.getEmail());
            ps.setInt(6, CATEGORY_ID);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Add mentor error!");
        }
    }

    @Override
    public void update(Mentor mentor) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "UPDATE user " +
                            "  SET login=?, password=?, name=?, surname=?, email=? " +
                            "    WHERE id=?;");
            ps.setString(1, mentor.getLogin());
            ps.setString(2, mentor.getPassword());
            ps.setString(3, mentor.getName());
            ps.setString(4, mentor.getSurname());
            ps.setString(5, mentor.getEmail());
            ps.setInt(6, mentor.getID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Update mentor error!");
        }
    }

    @Override
    public List<Mentor> getAll() throws DataAccessException {

        List<Mentor> mentors = new ArrayList<>();

        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT * FROM user WHERE user_category_id=2;");
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                mentors.add(createFrom(rs));
            }
            return mentors;

        } catch (SQLException e) {
            throw new DataAccessException("Get all mentors error!");
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "DELETE FROM user WHERE id=?;");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Delete mentor error!");
        }
    }

    @Override
    public Mentor get(int id) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT * FROM user WHERE id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return createFrom(rs);
        } catch (SQLException e) {
            throw new DataAccessException("Get mentor error!");
        }
    }

    private Mentor createFrom(ResultSet rs) throws DataAccessException {
        try {
            int id = rs.getInt("id");
            String login = rs.getString("login");
            String password = rs.getString("password");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String email = rs.getString("email");
            // TODO: create groupList
            return new Mentor(id, login, password, name, surname, email, null);
        } catch (SQLException e) {
            throw new DataAccessException("Create mentor error!");
        }
    }
}


