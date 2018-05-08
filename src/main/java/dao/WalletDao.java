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
    public boolean checkIfExist(int user_id) throws DataAccessException{
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT * FROM wallet WHERE user_id=?;");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e){
            throw new DataAccessException("Problem with checking if wallet exist");
        }
    }

    @Override
    public List<ArtifactModel> getArtifactByUserId(int user_id) throws DataAccessException{
        try {
            PreparedStatement ps = getConnection().prepareStatement(
                    "SELECT user_artifact.id as 'id', artifact.name as 'artifact name', artifact.description as 'description'," +
                            "artifact.price as 'price', artifact_category.name as 'category name', artifact_status.name as 'status' " +
                            "FROM user_artifact JOIN artifact ON user_artifact.artifact_id = artifact.id JOIN artifact_status ON " +
                            " user_artifact.status_id = artifact_status.id JOIN artifact_category ON artifact.category_id = artifact_category.id WHERE" +
                            " user_artifact.user_id = ?;");
            ps.setInt(1, user_id);
            ResultSet rs = ps.executeQuery();

            List<ArtifactModel> userArtifact = new ArrayList<>();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("artifact name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                String categoryName= rs.getString("category name");
                String status = rs.getString("status");
                ArtifactModel artifact = new ArtifactModel(id, name, description, price, categoryName, status);
                userArtifact.add(artifact);
            }
            return userArtifact;
        } catch (SQLException e){
            throw new DataAccessException("Problem with getting user artifact");
        }
    }

    @Override
    public List<WalletModel> getWalletsCollection() throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM wallet");
            List<WalletModel> wallets = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("user_id");
                int totalCoinsEarned = rs.getInt("total_coins_earned");
                int amount = rs.getInt("amount");
                ArrayList<ArtifactModel> artifacts = (ArrayList<ArtifactModel>) getArtifactByUserId(id);
                WalletModel wallet = new WalletModel(id, amount, totalCoinsEarned, artifacts);
                wallets.add(wallet);
            }
            return wallets;
        } catch (SQLException e){
            throw new DataAccessException("Problem with getting user artifact");
        }
    }
}
