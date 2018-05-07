package dao;

import model.ArtifactModel;
import model.Iterator;
import model.WalletModel;

import java.util.ArrayList;

public interface WalletDaoInterface {
    void addWallet(WalletModel wallet);
    void updateWallet(WalletModel wallet);
    WalletModel getByID(int id);
    boolean checkIfExist(int user_id);
    ArrayList<ArtifactModel> getArtifactByUserId(int user_id);
    Iterator getIterator();
}
