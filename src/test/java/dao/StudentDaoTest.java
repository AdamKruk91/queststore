package dao;

import model.Student;
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
    private Student mockStudent = Mockito.mock(Student.class);

    @Mock
    private Wallet wallet = Mockito.mock(Wallet.class);

//    @Test
//    void getStudentByLoginIdReturnsGoodIdTest(){
//        Mockito.when(mockStudent.getID()).thenReturn(1);
//        assertEquals(mockStudent.getID(), studentDao.get(4).getID());
//    }

    @Test
    void getStudentsCollection() {
    }

    @Test
    void insertNewStudent() {
    }

//    @Test
//    void updateWallet() {
//        Mockito.when(wallet.getTotalCoinsEarned()).thenReturn(200);
//        Mockito.when(wallet.getAmount()).thenReturn(100);
//        Mockito.when(mockStudent.getID()).thenReturn(1);
//        Mockito.when(mockStudent.getWallet()).thenReturn(wallet);
//
//        studentDao.update(mockStudent);
//        StudentModel testStudent = studentDao.getStudentById(4);
//
//        assertEquals(mockStudent.getMyWallet().getTotalCoolcoins(), testStudent.getMyWallet().getTotalCoolcoins());
//    }
}