package dao;

import model.ItemModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class TransactionDaoTest extends TestableDatabaseUnit{

    private static TransactionDao transactionDao = new TransactionDao();

    @AfterAll
    static void afterAll(){
        transactionDao.executeUpdate("BEGIN TRANSACTION;");
    }

    @Test
    void testUpdateStatusOfTransaction() {
        ItemModel mockItem = Mockito.mock(ItemModel.class);
        Mockito.when(mockItem.getID()).thenReturn(1);
        transactionDao.updateStatusOfTransaction(mockItem);

        String query = "SELECT count(*) as rows_count FROM Transactions WHERE id_item = 1;";
        ResultSet rs = transactionDao.executeSelect(query);
        try {
            int i = rs.getInt("rows_count");
            assertEquals(4, i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetStudentArtifact() {
        List<ItemModel> itemModel = transactionDao.getStudentArtifact(2);

        assertEquals(itemModel.get(0).getID(), 5);
        assertEquals(itemModel.get(1).getID(), 3);

    }


}