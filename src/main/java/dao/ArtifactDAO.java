package dao;

import exceptions.DataAccessException;
import model.Artifact;
import model.UsableObject;

import java.util.List;

public interface ArtifactDAO {

    Artifact getArtifact(int artifactID) throws DataAccessException;
    Artifact getInstantiatedArtifact(int artifactID) throws DataAccessException;
    List<Artifact> getArtifactCollection() throws DataAccessException;
    void addArtifact(Artifact artifact) throws DataAccessException;
    void removeArtifact(Artifact artifact) throws DataAccessException;
    void updateArtifact(Artifact artifact) throws DataAccessException;
    List<Artifact> getUserUnusedArtifacts(int userID) throws DataAccessException;
    List<Artifact> getUserUsedArtifacts(int userID) throws DataAccessException;
    List<Artifact> getUserRequestedArtifacts(int userID) throws DataAccessException;
    List<Artifact> getArtifacts(int user_id) throws DataAccessException;
    void updateArtifactStatus(Artifact artifact) throws DataAccessException;
    void insertTransaction(int userID, int artifactID) throws DataAccessException;
}
