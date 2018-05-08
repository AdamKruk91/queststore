package dao;

import exceptions.DataAccessException;
import model.ArtifactModel;
import model.UserModel;

import java.util.List;

public interface ArtifactDaoInterface {

    ArtifactModel getArtifactById(int artifactID) throws DataAccessException;
    List<ArtifactModel> getArtifactCollection();
    void addArtifact(ArtifactModel artifact);
    void removeArtifact(ArtifactModel artifact);
    void updateArtifact(ArtifactModel artifact);
    List<ArtifactModel> getUserUnusedArtifacts(int userID);
    public List<ArtifactModel> getUserUsedArtifacts(int userID);
    public List<ArtifactModel> getUserRequestedArtifacts(int userID);
    void addArtifactToWallet(int userID, int artifact_id);
    void updateArtifactStatus(int userID, ArtifactModel artifact);
}
