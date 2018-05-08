package dao;

import model.ArtifactModel;
import model.UsableObjectModel;
import model.QuestModel;

import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;


public class ItemDao extends ManipulationDao{


    public void insertNewItem(UsableObjectModel item) throws SQLException {
        String table = "Item";
        String columns = " ('item_name', 'description', 'price', 'id_type')";
        String values = "('" + item.getName() + "','" + item.getDescription() + "'," + item.getValue() + ", " + findIdType(item.getType()) + ")";
        insertDataIntoTable(table, columns, values);
    }

    public int findIdType(String typeName) throws SQLException {
        ResultSet result = selectDataFromTable("ItemType", "id_type", "name='"+typeName+"'");
        return getIntFromResult(result, "id_type");
    }

    public void updateValueOfItem(UsableObjectModel item) {
        int value = item.getValue();
        String name = item.getName();
        updateDataInTable("Item", "price=" + value, "item_name ='" + name + "'");
    }

    public UsableObjectModel createItemObject(ResultSet result, String typeName) {
        UsableObjectModel item = null;
        try {
            int idItem = result.getInt("id_item");
            String name = result.getString("item_name");
            String description = result.getString("description");
            int price = result.getInt("price");

            if (typeName.equals("Quest"))
                item = new QuestModel(idItem, typeName, name, description, price);
            else if (typeName.equals("Artifact"))
                item = new ArtifactModel(idItem, typeName, name, description, price);
        } catch(SQLException e){
                e.printStackTrace();
        }
        return item;
    }

    private List<UsableObjectModel> fillCollection(ResultSet result, String typeName) {
        List<UsableObjectModel> itemCollection = new ArrayList<>();
        try {
            while (result.next()) {
                UsableObjectModel item = createItemObject(result, typeName);
                itemCollection.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemCollection;
    }

    public List<UsableObjectModel> getItemCollectionByType(String typeName) throws SQLException {
        int idType = findIdType(typeName);
        String columns = "id_item, item_name, description, price";
        String condition = "id_type='" + idType + "'";
        ResultSet result = selectDataFromTable("Item", columns, condition);

        return fillCollection(result, typeName);
    }


    public List<UsableObjectModel> selectStudentsItems(int selectedStudentId, String typeName) throws SQLException {
        int idType = findIdType(typeName);
        String columns = "Transactions.id_item, Transactions.id_student, Transactions.used, item_name, description, price, id_type";
        String joinStatement = "Transactions.id_item = Item.id_item";
        String condition = "id_student = " + selectedStudentId + " AND id_type =" + idType + " AND used=0";
        ResultSet result = selectFromJoinedTablesWithCondition(columns, "Item", "Transactions", joinStatement, condition);
        return fillCollection(result, typeName);
    }

    public UsableObjectModel getItemByID(int itemID) {
        String columns = "id_item, item_name, description, price";
        String joinStatement = "item.id_type = ItemType.id_type";
        String condition = " id_item ="+itemID;
        ResultSet result = selectFromJoinedTablesWithCondition(columns, "Item", "ItemType", joinStatement, condition);
        return createItemObject(result, "Artifact");
    }
}