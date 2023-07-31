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

  public DAOBase() {
  }

  private Connection _connection;
  private static final String url = "jdbc:mysql://db:3306/ripetizioni?useSSL=false";
  // private static final String url =
  // "jdbc:mysql://localhost:3306/ripetizioni?autoReconnect=true&useSSL=false";

  private static void registerDriver() {
    try {
      DriverManager.registerDriver(new com.mysql.jdbc.Driver());
      System.out.println("Driver not registered");
    } catch (SQLException e) {
      System.out.println("Unexpected error: " + e.getMessage());
    }
  }

  private void startConnection() {
    try {
      _connection = DriverManager.getConnection(url, "admin", "admin1");
      if (_connection != null) {
        System.out.println("Connected to the database");
      }
    } catch (SQLException exc) {
      System.out.println(exc.getMessage());
    }
  }

  private void endConnection() {
    try {
      if (_connection != null) {
        _connection.close();
        System.out.println("Connection to the database closed");
      }
    } catch (SQLException exc) {
      System.out.println(exc.getMessage());
    }
  }

  public CachedRowSet executeQuery(String query, Object... args) throws IllegalArgumentException {
    checkParametersCount(query, args);
    query = replaceInPlaceholder(query, args);
    synchronized (this) {
      startConnection();
      try (PreparedStatement stmt = _connection.prepareStatement(query)) {
        // Set parameters
        for (int i = 0; i < args.length; i++) {
          stmt.setObject(i + 1, args[i]);
        }
        System.out.println(stmt.toString());
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
    }
    return null;
  }

  public CachedRowSet executeQuery(String query) {
    return executeQuery(query, new Object[] {});
  }

  public int executeUpdateQuery(String query, Object... args) {
    checkParametersCount(query, args);
    query = replaceInPlaceholder(query, args);
    startConnection();
    try (PreparedStatement stmt = _connection.prepareStatement(query)) {
      // Set parameters
      for (int i = 0; i < args.length; i++) {
        stmt.setObject(i + 1, args[i]);
      }
      System.out.println(stmt.toString());
      return stmt.executeUpdate();
    } catch (SQLException exc) {
      System.out.println(exc.getMessage());
    } finally {
      endConnection();
    }
    return 0;
  }

  public int executeUpdateQuery(String query) throws IllegalArgumentException {
    return executeUpdateQuery(query, new Object[] {});
  }

  private static void checkParametersCount(String query, Object... params) throws IllegalArgumentException {
    if (query.contains("(?)")) {
      return;
    }
    var placeholderCount = query.chars().filter(ch -> ch == '?').count();
    if (placeholderCount != params.length) {
      throw new IllegalArgumentException(
          "The number of placeholders in the query does not match the number of parameters");
    }
  }

  private static String replaceInPlaceholder(String query, Object... params) {
    // Substitute (?) placeholder with ? as number of params
    var parameterOutsideIn = query.replace("(?)", "").chars().filter(ch -> ch == '?').count();
    var newPlaceholder = new StringBuilder();
    for (int i = 0; i < (params.length - parameterOutsideIn) - 1; i++) {
      newPlaceholder.append("?,");
    }
    newPlaceholder.append("?");
    return query.replace("'", "").replace("(?)", "(" + newPlaceholder + ")");
  }
}
