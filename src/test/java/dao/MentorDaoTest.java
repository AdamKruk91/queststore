package dao;


import model.MentorModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MentorDaoTest extends TestableDatabaseUnit {

    private static MentorDao dao = new MentorDao();

    @AfterAll
    static void afterAll() {
        dao.executeUpdate("BEGIN TRANSACTION;");
    }

    @Test
    void testMentorInsertion() {
        MentorModel mentor = new MentorModel(
                "TestMan",
                "SuperMan",
                "superman@test.com",
                "superpassword",
                1);

        dao.insertNewMentor(mentor);
        String query =
                "SELECT first_name, last_name, Mentor.id_login, Mentor.id_status, id_group, email, password " +
                " FROM Mentor" +
                "  INNER JOIN Login ON Mentor.id_login = Login.id_login" +
                "   WHERE first_name LIKE 'TestMan' AND last_name LIKE 'SuperMan' AND email LIKE 'superman@test.com' AND password LIKE 'superpassword'" +
                "    AND Mentor.id_login NOT NULL AND Mentor.id_status NOT NULL AND id_group NOT NULL;";
        ResultSet rs = dao.executeSelect(query);
        boolean insertionSuccessful = false;
        try {
            insertionSuccessful = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertTrue(insertionSuccessful);
    }

    @Test
    void testSQLInjection() {
        MentorModel mentor = new MentorModel(
                "TestMan",
                "SuperMan', 333, 444, 555); " +
                        "  INSERT INTO Admin (first_name, last_name, id_login, id_status) " +
                        "    VALUES ('Lord', 'Satan', 666,666);--",
                "superman@test.com",
                "superpassword",
                1);

        dao.insertNewMentor(mentor);
        String query =
                "SELECT * FROM Admin WHERE last_name LIKE 'Satan';";
        ResultSet rs = dao.executeSelect(query);
        boolean injectionSuccessful = false;
        try {
            injectionSuccessful = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        assertFalse(injectionSuccessful);
    }
}