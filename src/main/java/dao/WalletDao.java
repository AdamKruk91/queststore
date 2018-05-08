package dao;

import exceptions.DataAccessException;
import model.ArtifactModel;
import model.WalletModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WalletDao extends ManipulationDao implements WalletDaoInterface{

    @Override
    public void addWallet(WalletModel wallet) throws DataAccessException{
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "INSERT INTO wallet (user_id, total_coins_earned, amount) " +
                            "VALUES(?, ?, ?)");
            ps.setInt(1, wallet.getID());
            ps.setInt(2, wallet.getTotalCoinsEarned());
            ps.setInt(3, wallet.getAmount());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("Problem with adding new wallet");
        }
    }

    @Override
    public void updateWallet(WalletModel wallet) throws DataAccessException{
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "UPDATE wallet SET total_coins_earned=?, amount=? WHERE user_id=?;");
            ps.setInt(1, wallet.getTotalCoinsEarned());
            ps.setInt(2, wallet.getAmount());
            ps.setInt(3, wallet.getID());
            ps.executeUpdate();
        } catch (SQLException e){
            throw new DataAccessException("Problem with updating wallet");
        }
    }

    @Override
    public WalletModel getByID(int id) throws DataAccessException{
        try {
            if (!checkIfExist(id)) {
                WalletModel wallet = new WalletModel(id);
                addWallet(wallet);
            }
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM wallet WHERE user_id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return getWalletFromResultSet(rs);
        }catch (SQLException e) {
            throw new DataAccessException("Problem with getting wallet by ID");
        }
    }

    private WalletModel getWalletFromResultSet(ResultSet rs) throws DataAccessException, SQLException {
        int user_id = rs.getInt("user_id");
        int amount = rs.getInt("amount");
        int total_coins_earned = rs.getInt("total_coins_earned");
        ArrayList<ArtifactModel> OwnedArtifacts = (ArrayList<ArtifactModel>) getArtifactByUserId(user_id);
        return new WalletModel(user_id, amount, total_coins_earned, OwnedArtifacts);
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
