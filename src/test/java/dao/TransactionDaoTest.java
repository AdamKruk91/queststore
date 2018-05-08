package dao;

import model.UsableObjectModel;
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
        UsableObjectModel mockItem = Mockito.mock(UsableObjectModel.class);
        Mockito.when(mockItem.getID()).thenReturn(1);
        transactionDao.updateStatusOfTransaction(mockItem, 1);

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
        List<UsableObjectModel> usableObjectModel = transactionDao.getStudentArtifact(2);

        assertEquals(usableObjectModel.get(0).getID(), 5);
        assertEquals(usableObjectModel.get(1).getID(), 3);

    }


}