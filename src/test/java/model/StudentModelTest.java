package model;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class StudentModelTest {

    @Mock
    private Wallet wallet = Mockito.mock(Wallet.class);
    private Group groupModel = Mockito.mock(Group.class);

    @Test
    void checkIfStudentModelHasWallet(){
        Student student = new Student(1, "Adam", "Kruk", "adam@gmal.com",
                                                "lala", "mail", wallet);
        assertEquals(wallet, student.getWallet());
    }

//    @Test
//    void checkIfStudentModelHasGroup(){
//        Student student = new Student(1, "Adam", "Kruk", "adam@gmal.com",
//                "lala", "mail", wallet);
//        assertEquals(groupModel, student.getGroup());
//    }
}