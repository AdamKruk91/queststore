package dao;

import exceptions.DataAccessException;
import model.Artifact;
import model.Wallet;

import java.util.List;

public interface WalletDAO {
    void add(Wallet wallet) throws DataAccessException;
    void update(Wallet wallet) throws DataAccessException;
    Wallet get(int id) throws DataAccessException;
    boolean checkIfExist(int user_id) throws DataAccessException;
    List<Wallet> getAll() throws DataAccessException;
}
