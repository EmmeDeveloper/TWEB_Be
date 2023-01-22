package app.dao;

import lombok.var;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.*;

public class DAOBase {

    // Executed once when the class is loaded
    static {
        registerDriver();
    }


    public DAOBase() {}
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

    public static CachedRowSet executeQuery(String query, Object... args) throws IllegalArgumentException {
        checkParametersCount(query, args);
        startConnection();
        try (PreparedStatement stmt = _connection.prepareStatement(query)) {
            // Set parameters
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            try (ResultSet rs = stmt.executeQuery()) {
                CachedRowSet crs = RowSetProvider.newFactory().createCachedRowSet();
                crs.populate(rs);
                return crs;
            }
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        } finally {
            endConnection();
        }
        return null;
    }


    public static CachedRowSet executeQuery(String query) {
        return executeQuery(query, new Object[]{});
    }

    public static int executeUpdateQuery(String query, Object... args) {
        checkParametersCount(query, args);
        startConnection();
        try (PreparedStatement stmt = _connection.prepareStatement(query)) {
            // Set parameters
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            return stmt.executeUpdate();
        } catch (SQLException exc) {
            System.out.println(exc.getMessage());
        } finally {
            endConnection();
        }
        return 0;
    }

    public static int executeUpdateQuery(String query) throws IllegalArgumentException {
        return executeUpdateQuery(query, new Object[]{});
    }

    private static void checkParametersCount(String query, Object... params) throws IllegalArgumentException {
        var placeholderCount = query.chars().filter(ch -> ch == '?').count();
        if (placeholderCount != params.length) {
            throw new IllegalArgumentException("The number of placeholders in the query does not match the number of parameters");
        }
    }

}
