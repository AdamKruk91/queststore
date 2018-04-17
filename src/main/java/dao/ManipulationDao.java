package dao;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;


public class ManipulationDao implements UserDaoInterface {

    private static DatabaseConnection database = DatabaseConnection.getInstance();
    private static Connection connection;
    private static Statement statement;

    public ManipulationDao(){
        connection = database.getConnection();
        try {
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ResultSet executeSelect(String sql) {
        ResultSet result = null;
        try {
            result = statement.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return result;
    }

    public void executeUpdate(String sql) {
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
    }

    public int getIntFromResult(ResultSet result, String columnName) {
        int intData = 0;
        try {
            intData = result.getInt(columnName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intData;
    }

    public String getStringFromResult(ResultSet result, String columnName) {
        String stringData = null;
        try {
            stringData = result.getString(columnName);
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        return stringData;
    }

    public ResultSet selectDataFromTable(String tableName, String columns, String condition) {
        String sql = "SELECT " + columns + " FROM " + tableName + " WHERE " + condition + ";";
        return executeSelect(sql);
    }

    public ResultSet selectFromJoinedTables(String columns, String tableName, String joinTable, String joinStatement) {
        String sql = "SELECT " + columns + " FROM " + tableName + " JOIN " + joinTable + " ON " + joinStatement + ";";
        return  executeSelect(sql);
    }
    
    public void insertDataIntoTable(String tableName, String columns, String values) {
        String sql = "INSERT INTO " + tableName + columns + " VALUES " + values;
        executeUpdate(sql);
    }

    public void updateDataInTable(String table, String setQuerry, String condition) {
        String sql = "UPDATE " + table + " SET " + setQuerry + " WHERE " + condition;
        executeUpdate(sql);
    }

    public ResultSet selectFromJoinedTablesWithCondition(String columns, String tableName, String joinTable, String joinStatement, String condition){
        String sql = "SELECT " + columns + " FROM " + tableName + " JOIN " + joinTable + " ON " + joinStatement + " WHERE " + condition +";";
        return executeSelect(sql);
    }

}

