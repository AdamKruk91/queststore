package dao;

import model.ArtifactModel;
import model.Iterator;
import model.WalletModel;

import java.util.ArrayList;

public class WalletDao extends ManipulationDao implements WalletDaoInterface{

    @Override
    public void addWallet(WalletModel wallet) {

    }

    @Override
    public void updateWallet(WalletModel wallet) {

    }

    @Override
    public WalletModel getByID(int id) {
        return null;
    }

    @Override
    public boolean checkIfExist(int user_id) {
        return false;
    }

    @Override
    public ArrayList<ArtifactModel> getArtifactByUserId(int user_id) {
        return null;
    }

    @Override
    public Iterator getIterator() {
        return null;
    }
}
