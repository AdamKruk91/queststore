package dao;

import exceptions.DataAccessException;
import model.Artifact;

import java.util.List;

public interface ArtifactDAO {

    Artifact getArtifactById(int artifactID) throws DataAccessException;
    List<Artifact> getArtifactCollection() throws DataAccessException;
    void add(Artifact artifact) throws DataAccessException;
    void remove(Artifact artifact) throws DataAccessException;
    void update(Artifact artifact) throws DataAccessException;
    List<Artifact> getUserUnusedArtifacts(int userID) throws DataAccessException;
    List<Artifact> getUserUsedArtifacts(int userID) throws DataAccessException;
    List<Artifact> getUserRequestedArtifacts(int userID) throws DataAccessException;
    List<Artifact> getArtifacts(int user_id) throws DataAccessException;
    void updateArtifactStatus(Artifact artifact) throws DataAccessException;
}
