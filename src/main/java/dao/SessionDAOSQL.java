package dao;

import exceptions.DataAccessException;
import model.Session;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAOSQL extends ManipulationDAOSQL implements SessionDAO {

    @Override
    public boolean sessionExists(String sessionID) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT * FROM session WHERE id=?;");
            ps.setString(1, sessionID);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new DataAccessException("Error during sessionExists");
        }
    }

    @Override
    public void addSession(Session session) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "INSERT INTO session (id, user_id) " +
                            "  VALUES (?, ?);");
            ps.setString(1, session.getSessionID());
            ps.setInt(2, session.getUserID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error during addSession");
        }
    }

    @Override
    public void deleteSession(int userID) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "DELETE FROM session WHERE user_id = ?");
            ps.setInt(1, userID);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error during deleteSession(userID)");
        }
    }

    @Override
    public Session getSession(int userID) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT * FROM session WHERE user_id = ?");
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            return createFromResultSet(rs);

        } catch (SQLException e) {
            throw new DataAccessException("Error during getSession(userID)");
        }
    }

    @Override
    public Session getSession(String sessionID) throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT * FROM session WHERE id = ?");
            ps.setString(1, sessionID);
            ResultSet rs = ps.executeQuery();
            return createFromResultSet(rs);

        } catch (SQLException e) {
            throw new DataAccessException("Error during getSession(sessionID)");
        }
    }

    private Session createFromResultSet(ResultSet rs) throws SQLException {
        String sessionID = rs.getString("id");
        int userID = rs.getInt("user_id");
        return new Session(sessionID, userID);
    }
}
