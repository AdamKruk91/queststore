package dao;

import model.ArtifactModel;
import model.UserModel;

import java.util.List;

public interface ArtifactDaoInterface {

    ArtifactModel getArtifactById(int id);
    List<ArtifactModel> getArtifactCollection();
    void addArtifact(ArtifactModel artifact);
    void removeArtifact(ArtifactModel artifact);
    void updateArtifact(ArtifactModel artifact);
    List<ArtifactModel> getUserUnusedArtifacts(int userID);
    void addArtifactToWallet(int user_id, int artifact_id);
    void updateArtifactStatus(UserModel currentUser, ArtifactModel artifact);
}
