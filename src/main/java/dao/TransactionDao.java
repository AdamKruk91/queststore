package dao;

import model.UsableObjectModel;
import model.ArtifactModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class TransactionDao extends ManipulationDao {

    public void insertTransaction(int idStudent, int idItem) {
        String values =  "("+ idStudent +", " + idItem+ "," + 0 + ")";
        insertDataIntoTable("Transactions ", "(id_student, id_item, used)", values);
    }

    public void updateStatusOfTransaction(UsableObjectModel item, int statusID) {
        int itemId = item.getID();
        updateDataInTable("Transactions", "used = "+statusID, "id_item="+itemId);
    }

    private String prepareGetArtifactsSql(int idStudent, int idStatus) {
        String columns = "Item.id_item, item_name, description, price, used";
        String joinStmt1 = "Item.id_item = Transactions.id_item";
        String joinStmt2 = "Item.id_type = ItemType.id_type";

        String sql = "SELECT " + columns + " FROM Transactions " +
                " JOIN Item ON " + joinStmt1 +
                " JOIN ItemType ON " + joinStmt2 +
                " WHERE id_student= '" + idStudent + "' AND ItemType.name='Artifact' AND used= " + idStatus + ";";
        return sql;
    }

    public UsableObjectModel getItemObject(ResultSet result) {
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

    public List<UsableObjectModel> getStudentArtifact(int idStudent, int idStatus) {
        String sql = prepareGetArtifactsSql(idStudent, idStatus);
        ResultSet result = executeSelect(sql);

        return getArtifactsFromResultSet(result);
    }

    public List<UsableObjectModel> getStudentArtifact(int idStudent) {
        String sql = prepareGetArtifactsSql(idStudent, 0);
        ResultSet result = executeSelect(sql);

        return getArtifactsFromResultSet(result);
    }

    private List<UsableObjectModel> getArtifactsFromResultSet(ResultSet result){
        List<UsableObjectModel> artifactsCollection = new ArrayList<>();
        try {
            while (result.next()) {
                UsableObjectModel artifact = getItemObject(result);
                artifactsCollection.add(artifact);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return artifactsCollection;
    }

}