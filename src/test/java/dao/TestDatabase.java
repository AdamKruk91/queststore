package dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.StringJoiner;

abstract class TestDatabase {

    static void prepareTestInstance() throws SQLException {
        final String DBPATH = "jdbc:sqlite:Testable.db";
        final String SCRIPTPATH = "TestDatabaseCreation.sql";

        DatabaseConnection.setDBNAME(DBPATH);

        Connection connection = DatabaseConnection.getInstance().getConnection();
        connection.setAutoCommit(false);

        String query = readScript(SCRIPTPATH);
        connection.createStatement().executeUpdate(query);

    }

    private static String readScript(String filepath) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(filepath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringJoiner joiner = new StringJoiner("\n");

        while(scanner.hasNext()) {
            joiner.add(scanner.nextLine());
        }

        return joiner.toString();
    }
}
