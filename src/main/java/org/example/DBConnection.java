package org.example;

import java.sql.*;

public class DBConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Vasu@123";

    public static Connection getConnection(String database) throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(e);
        }
        return DriverManager.getConnection(URL + database, USER, PASSWORD);
    }

    public static Connection getConnection() throws SQLException {
        return getConnection("library_management");
    }
}
