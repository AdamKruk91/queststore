package dao;

import model.ItemModel;
import model.ArtifactModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TransactionDao extends ManipulationDao implements TransationDaoInterface {

    public void insertTransaction(int idStudent, int idItem) {
        String values =  "("+ idStudent +", " + idItem+ "," + 0 + ")";
        insertDataIntoTable("Transactions ", "(id_student, id_item, used)", values);
    }

    public void updateStatusOfTransaction(ItemModel item) {
        int itemId = item.getID();
        updateDataInTable("Transactions", "used = 1", "id_item="+itemId);
    }

    private String prepareGetArtifactsSql(int idStudent) {
        String columns = "Item.id_item, item_name, description, price, used";
        String joinStmt1 = "Item.id_item = Transactions.id_item";
        String joinStmt2 = "Item.id_type = ItemType.id_type";

        String sql = "SELECT " + columns + " FROM Transactions " +
                " JOIN Item ON " + joinStmt1 +
                " JOIN ItemType ON " + joinStmt2 +
                " WHERE id_student= '" + idStudent + "' AND ItemType.name='Artifact' AND used=0";
        return sql;
    }

    public ItemModel getItemObject(ResultSet result) {
        ArtifactModel artifact = null;
        try {
            int id = result.getInt("id_item");
            String name = result.getString("item_name");
            String description = result.getString("description");
            int price = result.getInt("price");
            artifact = new ArtifactModel(id, "Artifact", name, description, price);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artifact;
    }

    public List<ItemModel> getStudentArtifact(int idStudent) {
        List<ItemModel> artifactsCollection = new ArrayList<>();
        String sql = prepareGetArtifactsSql(idStudent);
        ResultSet result = executeSelect(sql);
        try {
            while (result.next()) {
                ItemModel artifact = getItemObject(result);
                artifactsCollection.add(artifact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artifactsCollection;
    }
}