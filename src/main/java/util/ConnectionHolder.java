package util;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class ConnectionHolder {
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    public static Connection getConnection() throws SQLException {
        var connection = connectionHolder.get();
        connection = ConnectionManager.open();
        connectionHolder.set(connection);
        return connection;
    }

    public static void closeConnection() {
        var connection = connectionHolder.get();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            } finally {
                connectionHolder.remove();
            }
        }
    }
}
