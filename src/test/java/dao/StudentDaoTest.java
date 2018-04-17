package dao;

import model.StudentModel;
import model.WalletModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;


class StudentDaoTest extends TestableDatabaseUnit{

    private static StudentDao studentDao = new StudentDao();

    @AfterAll
    static void afterAll(){
        studentDao.executeUpdate("BEGIN TRANSACTION;");
    }

    @Mock
    private StudentModel mockStudent = Mockito.mock(StudentModel.class);

    @Mock
    private WalletModel wallet = Mockito.mock(WalletModel.class);

    @Test
    void getStudentByLoginIdReturnsGoodIdTest(){
        Mockito.when(mockStudent.getID()).thenReturn(1);
        assertEquals(mockStudent.getID(), studentDao.getStudentByIdLogin(4).getID());
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
        StudentModel testStudent = studentDao.getStudentByIdLogin(4);

        assertEquals(mockStudent.getMyWallet().getTotalCoolcoins(), testStudent.getMyWallet().getTotalCoolcoins());
    }
}