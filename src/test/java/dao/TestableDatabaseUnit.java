package dao;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import java.sql.SQLException;

class TestableDatabaseUnit {

    private ManipulationDao dao;

    @BeforeAll
    static void beforeAll() throws SQLException{
        TestDatabase.prepareTestInstance();
    }

    @BeforeEach
    void beforeEach() {
        dao = new ManipulationDao();
        dao.executeUpdate("BEGIN TRANSACTION;");
    }

    @AfterEach
    void afterEach() {
        dao.executeUpdate("ROLLBACK;");
    }
}