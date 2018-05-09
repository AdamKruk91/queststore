package dao;

import model.Wallet;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;


class StudentDaoTest extends TestableDatabaseUnit{

    private static StudentDAOSQL studentDao = new StudentDAOSQL();

    @AfterAll
    static void afterAll(){
        studentDao.executeUpdate("BEGIN TRANSACTION;");
    }

    @Mock
    private StudentModel mockStudent = Mockito.mock(StudentModel.class);

    @Mock
    private Wallet wallet = Mockito.mock(Wallet.class);

    @Test
    void getStudentByLoginIdReturnsGoodIdTest(){
        Mockito.when(mockStudent.getID()).thenReturn(1);
        assertEquals(mockStudent.getID(), studentDao.getStudentById(4).getID());
    }

    @Test
    void getStudentsCollection() {
    }

    @Test
    void insertNewStudent() {
    }

    @Test
    void updateWallet() {
        Mockito.when(wallet.getTotalCoolcoins()).thenReturn(200);
        Mockito.when(wallet.getBalance()).thenReturn(100);
        Mockito.when(mockStudent.getID()).thenReturn(1);
        Mockito.when(mockStudent.getMyWallet()).thenReturn(wallet);

        studentDao.updateWallet(mockStudent);
        StudentModel testStudent = studentDao.getStudentById(4);

        assertEquals(mockStudent.getMyWallet().getTotalCoolcoins(), testStudent.getMyWallet().getTotalCoolcoins());
    }
}