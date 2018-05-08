package dao;

import exceptions.DataAccessException;
import model.ArtifactModel;
import model.UserModel;

import java.util.List;

public interface ArtifactDaoInterface {

    ArtifactModel getArtifactById(int artifactID) throws DataAccessException;
    List<ArtifactModel> getArtifactCollection() throws DataAccessException;
    void addArtifact(ArtifactModel artifact) throws DataAccessException;
    void removeArtifact(ArtifactModel artifact) throws DataAccessException;
    void updateArtifact(ArtifactModel artifact);
    List<ArtifactModel> getUserUnusedArtifacts(int userID) throws DataAccessException;
    List<ArtifactModel> getUserUsedArtifacts(int userID) throws DataAccessException;
    List<ArtifactModel> getUserRequestedArtifacts(int userID) throws DataAccessException;
    List<ArtifactModel> getArtifacts(int user_id) throws DataAccessException;
    void updateArtifactStatus(ArtifactModel artifact) throws DataAccessException;
}
