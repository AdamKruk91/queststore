package dao;

import model.ItemModel;

import java.sql.ResultSet;
import java.util.List;

public interface TransationDaoInterface {
    void insertTransaction(int idStudent, int idItem);
    void updateStatusOfTransaction(ItemModel item);
    ItemModel getItemObject(ResultSet result);
    List<ItemModel> getStudentArtifact(int idStudent);
}


