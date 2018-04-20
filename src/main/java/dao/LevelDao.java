package dao;

import model.Iterator;
import model.Level;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class LevelDao {
    private Connection connection;

    public LevelDao() {
        DatabaseConnection database = DatabaseConnection.getInstance();
        connection = database.getConnection();
    }

    public void addLevel(String name, int exp) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO level (name, experience_amount) VALUES (?, ?);");
        ps.setString(1, name);
        ps.setInt(2, exp);
        ps.executeUpdate();
    }

    public void deleteLevel(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM level WHERE id_level = ?;");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public Iterator getLevels() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT id_level, name, experience_amount FROM Level ORDER BY experience_amount DESC;");
        ResultSet rs = ps.executeQuery();
        ArrayList<Level> levels = new ArrayList<>();
        while(rs.next()) {
            String name = rs.getString("name");
            int experience_amount = rs.getInt("experience_amount");
            int ID = rs.getInt("id_level");
            Level newLevel = new Level(ID, name, experience_amount);
            levels.add(newLevel);
        }
        return new Iterator<>(levels);
    }

    public Level getLevel(int totalExp) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM level " +
                        "  WHERE ? >= experience_amount " +
                        "    ORDER BY experience_amount DESC " +
                        "      LIMIT 1;");
        ps.setInt(1, totalExp);
        ResultSet rs = ps.executeQuery();

        int id = rs.getInt("id_level");
        String name = rs.getString("name");
        int expTier = rs.getInt("experience_amount");

        return new Level(id, name, expTier);
    }
}
