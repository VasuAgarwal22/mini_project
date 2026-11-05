package org.example;

import java.sql.*;

public class DBInitializer {
    public static void initializeDatabase() {

        try (Connection con = DBConnection.getConnection("postgres");
             Statement stmt = con.createStatement()) {

            ResultSet rs = stmt.executeQuery(
                    "SELECT 1 FROM pg_database WHERE datname = 'library_management'"
            );

            if (!rs.next()) {
                stmt.executeUpdate("CREATE DATABASE library_management");
                System.out.println("Database created successfully!");
            } else {
                System.out.println("Database already exists!");
            }

            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return;
        }

        createTables();
    }

    private static void createTables() {

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement()) {

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS books (
                    book_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    title VARCHAR(150) NOT NULL,
                    author VARCHAR(100) NOT NULL,
                    publisher VARCHAR(100),
                    category VARCHAR(50),
                    total_quantity INT NOT NULL,
                    available_quantity INT NOT NULL
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS members (
                    member_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    phone VARCHAR(15),
                    department VARCHAR(100)
                )
            """);

            stmt.executeUpdate("""
                CREATE TABLE IF NOT EXISTS issued_books (
                    issue_id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                    book_id BIGINT NOT NULL REFERENCES books(book_id),
                    member_id BIGINT NOT NULL REFERENCES members(member_id),
                    issue_date DATE NOT NULL,
                    due_date DATE NOT NULL,
                    return_date DATE,
                    status VARCHAR(20) DEFAULT 'ISSUED'
                )
            """);

            System.out.println("All tables created successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
