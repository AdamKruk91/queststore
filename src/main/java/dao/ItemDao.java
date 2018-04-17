package dao;

import model.ArtifactModel;
import model.ItemModel;
import model.QuestModel;

import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.sql.SQLException;


public class ItemDao extends ManipulationDao implements ItemDaoInterface {


    public void insertNewItem(ItemModel item) {
        String table = "Item";
        String columns = " ('item_name', 'description', 'price', 'id_type')";
        String values = "('" + item.getName() + "','" + item.getDescription() + "'," + item.getValue() + ", " + findIdType(item.getType()) + ")";
        insertDataIntoTable(table, columns, values);
    }

    public int findIdType(String typeName) {
        ResultSet result = selectDataFromTable("ItemType", "id_type", "name='"+typeName+"'");
        return getIntFromResult(result, "id_type");
    }

    public void updateValueOfItem(ItemModel item) {
        int value = item.getValue();
        String name = item.getName();
        updateDataInTable("Item", "price=" + value, "item_name ='" + name + "'");
    }

    public ItemModel createItemObject(ResultSet result, String typeName) {
        ItemModel item = null;
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

    private List<ItemModel> fillCollection(ResultSet result, String typeName) {
        List<ItemModel> itemCollection = new ArrayList<>();
        try {
            while (result.next()) {
                ItemModel item = createItemObject(result, typeName);
                itemCollection.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemCollection;
    }

    public List<ItemModel> getItemCollectionByType(String typeName) {
        int idType = findIdType(typeName);
        String columns = "id_item, item_name, description, price";
        String condition = "id_type='" + idType + "'";
        ResultSet result = selectDataFromTable("Item", columns, condition);

        return fillCollection(result, typeName);
    }


    public List<ItemModel> selectStudentsItems(int selectedStudentId, String typeName) {
        int idType = findIdType(typeName);
        String columns = "Transactions.id_item, Transactions.id_student, Transactions.used, item_name, description, price, id_type";
        String joinStatement = "Transactions.id_item = Item.id_item";
        String condition = "id_student = " + selectedStudentId + " AND id_type =" + idType + " AND used=0";
        ResultSet result = selectFromJoinedTablesWithCondition(columns, "Item", "Transactions", joinStatement, condition);
        return fillCollection(result, typeName);
    }
}