package dao;

import exceptions.DataAccessException;
import model.Artifact;
import model.Wallet;

import java.util.List;

public interface WalletDAO {
    void addWallet(Wallet wallet) throws DataAccessException;
    void updateWallet(Wallet wallet) throws DataAccessException;
    Wallet getByID(int id) throws DataAccessException;
    boolean checkIfExist(int user_id) throws DataAccessException;
    List<Artifact> getArtifactByUserId(int user_id) throws DataAccessException;
    List<Wallet> getWalletsCollection() throws DataAccessException;
}
