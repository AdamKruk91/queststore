package dao;

import exceptions.DataAccessException;
import model.IteratorImpl;
import model.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LevelDAOSQL implements LevelDAO{
    private Connection connection;

    public LevelDAOSQL() {
        DatabaseConnection database = DatabaseConnection.getInstance();
        connection = database.getConnection();
    }

    public void add(String name, int exp) throws DataAccessException {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO level (name, experience_amount) VALUES (?, ?);");
            ps.setString(1, name);
            ps.setInt(2, exp);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Add level failed!");
        }
    }

    public void remove(int id) throws DataAccessException {
        try{
            PreparedStatement ps = connection.prepareStatement(
                    "DELETE FROM level WHERE id_level = ?;");
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException("Add level failed!");
        }
    }

    public IteratorImpl getLevels() throws DataAccessException {
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT id_level, name, experience_amount FROM Level ORDER BY experience_amount DESC;");
            ResultSet rs = ps.executeQuery();
            ArrayList<Level> levels = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("name");
                int experience_amount = rs.getInt("experience_amount");
                int ID = rs.getInt("id_level");
                Level newLevel = new Level(ID, name, experience_amount);
                levels.add(newLevel);
            }
            return new IteratorImpl<>(levels);
        } catch (SQLException e) {
            throw new DataAccessException("Add level failed!");
        }
    }

    public Level getLevel(int totalExp) throws DataAccessException {
            try {
                PreparedStatement ps = connection.prepareStatement(
                        "SELECT * FROM level " +
                                "  WHERE ? >= experience_amount " +
                                "    ORDER BY experience_amount DESC " +
                                "      LIMIT 1;");
                ps.setInt(1, totalExp);
                ResultSet rs = ps.executeQuery();

                int id = rs.getInt("id");
                String name = rs.getString("name");
                int expTier = rs.getInt("experience_amount");

                return new Level(id, name, expTier);
            } catch (SQLException e) {
                throw new DataAccessException("Add level failed!");
            }
        }
}


