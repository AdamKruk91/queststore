package dao;

import exceptions.DataAccessException;
import model.ArtifactModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtifactDao extends ManipulationDao implements ArtifactDaoInterface {


    public ArtifactModel getArtifactById(int artifactID) throws DataAccessException{
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT artifact.id as 'id', artifact.name as 'name', description, price, artifact_category.name as 'category'\n" +
                            "FROM artifact\n" +
                            "INNER JOIN artifact_category ON category_id = `artifact_category`.id\n" +
                            "WHERE artifact.id = ?;");
            ps.setInt(1, artifactID);

            ResultSet rs = ps.executeQuery();
            return getArtifactWithoutStatus(rs);
        } catch(SQLException e){
            throw new DataAccessException("Create artifact object error");
        }

    }

    public List<ArtifactModel> getArtifactCollection() throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT user_artifact.id as 'id', artifact.name as 'artifact name', artifact.description as 'description'," +
                            "artifact.price as 'price', artifact_category.name as 'category name', artifact_status.name as 'status' " +
                            "FROM user_artifact JOIN artifact ON user_artifact.artifact_id = artifact.id JOIN artifact_status ON " +
                            " user_artifact.status_id = artifact_status.id JOIN artifact_category ON artifact.category_id = artifact_category.id;");
            ResultSet rs = ps.executeQuery();
            return getArtifacts(rs);
        } catch (SQLException e){
            throw new DataAccessException("Problem with getting artifact");
        }
    }

    public void addArtifact(ArtifactModel artifact) throws DataAccessException {
        try{
            PreparedStatement ps = getConnection().prepareStatement(
                    "INSERT INTO `artifact`(`name`,`description`,`price`,`category_id`)" +
                    " VALUES (?, ?, ?, ?);");
            ps.setString(1, artifact.getName());
            ps.setString(2, artifact.getDescription());
            ps.setInt(3, artifact.getPrice());
            int category = getCategoryID(artifact.getCategory());
            ps.setInt(4, category);
        }catch(SQLException e){
            throw new DataAccessException("Add artifact error");
        }

    }

    public void removeArtifact(ArtifactModel artifact) {

    }

    public void updateArtifact(ArtifactModel artifact) {

    }

    public List<ArtifactModel> getArtifacts(int user_id) throws DataAccessException{
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT user_artifact.id as 'id', artifact.name as 'artifact name', artifact.description as 'description'," +
                            "artifact.price as 'price', artifact_category.name as 'category name', artifact_status.name as 'status' " +
                            "FROM user_artifact JOIN artifact ON user_artifact.artifact_id = artifact.id JOIN artifact_status ON " +
                            " user_artifact.status_id = artifact_status.id JOIN artifact_category ON artifact.category_id = artifact_category.id WHERE" +
                            " user_artifact.user_id = ?;");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            return getArtifacts(rs);
        } catch (SQLException e){
            throw new DataAccessException("Problem with getting user artifact");
        }
    }

    public List<ArtifactModel> getUserUnusedArtifacts(int userID) throws DataAccessException {
        int IN_WALLET = 2;
        return getArtifacts(userID, IN_WALLET);
    }

    public List<ArtifactModel> getUserUsedArtifacts(int userID) throws DataAccessException{
        int USED = 1;
        return getArtifacts(userID, USED);
    }

    public List<ArtifactModel> getUserRequestedArtifacts(int userID) throws DataAccessException{
        int REQUEST = 3;
        return getArtifacts(userID, REQUEST);
    }

    public void updateArtifactStatus(ArtifactModel artifact) throws DataAccessException{
        try{
        PreparedStatement ps = getConnection().prepareStatement(
                "UPDATE user_artifact " +
                        "SET status_id = ? " +
                        "WHERE id = ?;");
        int statusID = getStatusID(artifact.getStatus());
        ps.setInt(1, statusID);
        ps.setInt(2, artifact.getID());
        }catch(SQLException e){
            throw new DataAccessException("Update artifact error");
        }
    }

    private int getStatusID(String status) {
        return 0;
    }

    private ArtifactModel getArtifactWithoutStatus(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        int price = rs.getInt("price");
        String category = rs.getString("category");

        return new ArtifactModel(id, name, description, price, category);
    }

    private ArtifactModel getArtifactWithStatus(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("artifact name");
        String description = rs.getString("description");
        int price = rs.getInt("price");
        String categoryName= rs.getString("category name");
        String status = rs.getString("status");
        return new ArtifactModel(id, name, description, price, categoryName, status);
    }

    private List<ArtifactModel> getArtifacts(int userID, int statusID) throws DataAccessException{
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT user_artifact.id as 'id', artifact.name as 'artifact name', artifact.description as 'description'," +
                            "artifact.price as 'price', artifact_category.name as 'category name', artifact_status.name as 'status' " +
                            "FROM user_artifact JOIN artifact ON user_artifact.artifact_id = artifact.id JOIN artifact_status ON " +
                            " user_artifact.status_id = artifact_status.id JOIN artifact_category ON artifact.category_id = artifact_category.id WHERE" +
                            " user_artifact.user_id = ? AND user_artifact.status_id = ?;");
            ps.setInt(1, userID);
            ps.setInt(2, statusID);
            ResultSet rs = ps.executeQuery();
            return getArtifacts(rs);
        } catch (SQLException e){
            throw new DataAccessException("Problem with getting user artifact");
        }
    }

    private List<ArtifactModel> getArtifacts(ResultSet rs) throws SQLException{
        List<ArtifactModel> userArtifact = new ArrayList<>();
        while(rs.next()){
            userArtifact.add(getArtifactWithStatus(rs));
        }
        return userArtifact;
    }

    private int getCategoryID(String category) throws DataAccessException{
        try{
            PreparedStatement ps = getConnection().prepareStatement("SELECT id " +
                    "FROM artifact_category " +
                    "WHERE name = ?");
            ps.setString(1, category);
            return ps.executeQuery().getInt("id");
        }catch(SQLException e){
            throw new DataAccessException("Get status error");
        }
    }
}
