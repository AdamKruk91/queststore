package dao;

import model.ItemModel;

import java.sql.ResultSet;
import java.util.List;

public interface ItemDaoInterface {

    void insertNewItem(ItemModel item);
    int findIdType(String typeName);
    List<ItemModel> getItemCollectionByType(String typeName);
    void updateValueOfItem(ItemModel item);
    ItemModel createItemObject(ResultSet result, String typeName);
    List<ItemModel> selectStudentsItems(int selectedStudentId, String typeName);

}
