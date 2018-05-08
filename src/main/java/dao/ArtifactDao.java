package dao;

import exceptions.DataAccessException;
import model.ArtifactModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArtifactDao extends ManipulationDao implements ArtifactDaoInterface {


    public ArtifactModel getArtifactById(int artifactID) throws DataAccessException{
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT artifact.id as 'id', artifact.name as 'name', description, price, artifact_category.name as 'category'\n" +
                            "FROM artifact\n" +
                            "INNER JOIN artifact_category ON category_id = `artifact_category`.id\n" +
                            "WHERE artifact.id = ?;";
            ps.setInt(1, artifactID);

            ResultSet rs = ps.executeQuery();
            return getArtifactWithoutStatus(rs);
        } catch(SQLException e){
            throw new DataAccessException("Create artifact object error");
        }

    }

    public List<ArtifactModel> getArtifactCollection() {
        return null;
    }

    public void addArtifact(ArtifactModel artifact) {

    }

    public void removeArtifact(ArtifactModel artifact) {

    }

    public void updateArtifact(ArtifactModel artifact) {

    }

    public List<ArtifactModel> getUserUnusedArtifacts(int userID) {
        return null;
    }

    public List<ArtifactModel> getUserUsedArtifacts(int userID){
        return null;
    }

    public List<ArtifactModel> getUserRequestedArtifacts(int userID){
        return null;
    }

    public void addArtifactToWallet(int userID, int artifactID) {

    }

    public void updateArtifactStatus(int userID, ArtifactModel artifact) {

    }

    private ArtifactModel getArtifactWithoutStatus(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        int price = rs.getInt("price");
        String category = rs.getString("category");

        return new ArtifactModel(id, name, description, price, category);
    }
}
