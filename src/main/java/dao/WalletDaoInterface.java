package dao;

import exceptions.DataAccessException;
import model.ArtifactModel;
import model.WalletModel;

import java.util.List;

public interface WalletDaoInterface {
    void addWallet(WalletModel wallet) throws DataAccessException;
    void updateWallet(WalletModel wallet) throws DataAccessException;
    WalletModel getByID(int id) throws DataAccessException;
    boolean checkIfExist(int user_id) throws DataAccessException;
    List<ArtifactModel> getArtifactByUserId(int user_id) throws DataAccessException;
    List<WalletModel> getWalletsCollection() throws DataAccessException;
}
