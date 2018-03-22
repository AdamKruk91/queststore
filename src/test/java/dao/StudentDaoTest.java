package dao;

import model.GroupModel;
import model.StudentModel;
import model.UserModel;
import model.WalletModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class StudentDaoTest extends TestableDatabaseUnit{

    private StudentDao studentDao = new StudentDao();

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
    void testGetByID1throwsIllegalArgumentEx() {
        assertThrows(IllegalArgumentException.class, () -> {studentDao.getStudentByIdLogin(0);});
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