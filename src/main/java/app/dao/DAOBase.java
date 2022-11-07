package app.dao;

import java.sql.*;

public class DAOBase {

    public DAOBase() {
        registerDriver();
    }
    private static Connection _connection;
    private static final String url = "jdbc:mysql://localhost:3306/ripetizioni";

    private static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver not registered");
        } catch (SQLException e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void startConnection() {
        try {
            _connection = DriverManager.getConnection(url, "root", "");
            if(_connection != null) {
                System.out.println("Connected to the database");
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }

    private static void endConnection() {
        try {
            if(_connection != null) {
                _connection.close();
                System.out.println("Connection to the database closed");
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
    }

    public static ResultSet executeQuery(String query) throws Exception {
        startConnection();
        try {
            Statement stx = _connection.createStatement();
            return stx.executeQuery(query);
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
        return null;
    }

    public static ResultSet executeQuery(String query, Object... args) throws Exception {
        String _query = String.format(query.replace("?", "%s"), args);
        return executeQuery(_query);
    }

    public static int executeUpdateQuery(String query) throws Exception {
        startConnection();
        try {
            Statement stx = _connection.createStatement();
            return stx.executeUpdate(query);
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        }
        endConnection();
        return 0;
    }

    public static int executeUpdateQuery(String query, Object... args) throws Exception {
        String _query = String.format(query.replace("?", "%s"), args);
        return executeUpdateQuery(_query);
    }

}
