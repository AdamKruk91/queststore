package dao;

import exceptions.DataAccessException;
import model.Artifact;
import model.Wallet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WalletDAOSQL extends ManipulationDAOSQL implements WalletDAO {

    ArtifactDAO artifactDAO = new ArtifactDAOSQL();

    @Override
    public void add(Wallet wallet) throws DataAccessException{
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
    public void update(Wallet wallet) throws DataAccessException{
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
    public Wallet get(int id) throws DataAccessException{
        try {
            if (!checkIfExist(id)) {
                Wallet wallet = new Wallet(id);
                add(wallet);
            }
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM wallet WHERE user_id=?;");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return createFrom(rs);
        }catch (SQLException e) {
            throw new DataAccessException("Problem with getting wallet by ID");
        }
    }

    private Wallet createFrom(ResultSet rs) throws DataAccessException, SQLException {
        int user_id = rs.getInt("user_id");
        int amount = rs.getInt("amount");
        int total_coins_earned = rs.getInt("total_coins_earned");
        ArrayList<Artifact> OwnedArtifacts = (ArrayList<Artifact>) artifactDAO.getArtifacts(user_id);
        return new Wallet(user_id, amount, total_coins_earned, OwnedArtifacts);
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
    public List<Wallet> getAll() throws DataAccessException {
        try {
            PreparedStatement ps = getConnection().prepareStatement("SELECT * FROM wallet");
            List<Wallet> wallets = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("user_id");
                int totalCoinsEarned = rs.getInt("total_coins_earned");
                int amount = rs.getInt("amount");
                ArrayList<Artifact> artifacts = (ArrayList<Artifact>) artifactDAO.getArtifacts(id);
                Wallet wallet = new Wallet(id, amount, totalCoinsEarned, artifacts);
                wallets.add(wallet);
            }
            return wallets;
        } catch (SQLException e){
            throw new DataAccessException("Problem with getting user artifact");
        }
    }
}
