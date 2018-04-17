package dao;

import java.sql.*;


public class DatabaseConnection {

    private static DatabaseConnection instance = null;
    private static Connection connection = null;
    private static String DBNAME = "jdbc:sqlite:Queststore.db";

    private DatabaseConnection() {
        getConnectionToDatabase();
    }
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public static void setDBNAME(String DBNAME) {
        DatabaseConnection.DBNAME = DBNAME;
    }

    private void getConnectionToDatabase () {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(DBNAME);
        } catch (SQLException | NullPointerException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("DB connection problem.");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.out.println("Class not found.");
        }
    }

    public Connection getConnection () {
        return connection;
    }

    public void closeConnection() {
        if (this.connection != null){
            try{
                this.connection.close();
            }catch (SQLException e){
                System.out.println("Unable to close!");
            }
        }
    }
}