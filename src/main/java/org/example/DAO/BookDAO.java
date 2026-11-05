package org.example.DAO;

import org.example.DBConnection;
import org.example.model.Book;

import java.sql.*;

public class BookDAO {

    public void addBook(Book book){

        String query = """
                 INSERT INTO books
                 (title, author, publisher, category,
                 total_quantity, available_quantity)
                 VALUES (?, ?, ?, ?, ?, ?)
                """;

        try(Connection connection = DBConnection.getConnection();) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1,book.getTitle());
            statement.setString(2,book.getAuthor());
            statement.setString(3,book.getPublisher());
            statement.setString(4,book.getCategory());
            statement.setInt(5,(book.getTotalQuantity()));
            statement.setInt(6, (book.getAvailableQuantity()));
            int rows = statement.executeUpdate();
            if(rows>0){
                System.out.println("Book Added succesfully");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void viewAllBooks(){
        String query = "SELECT * FROM BOOKS";
        try(Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString("title"));
                System.out.println(resultSet.getString("author"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Book searchBookById(Long id){
        String query = "SELECT * FROM books WHERE book_id = ?";
        try(Connection connection = DBConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1,id);
            try (ResultSet set = statement.executeQuery()){
                if(set.next()){
                    return Book.builder()
                            .bookId(set.getLong("book_id"))
                            .title(set.getString("title"))
                            .author(set.getString("author"))
                            .publisher(set.getString("publisher"))
                            .category(set.getString("category"))
                            .totalQuantity(set.getInt("total_quantity"))
                            .availableQuantity(set.getInt("available_quantity"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateBook(Book book) throws SQLException {
        String query = """
            UPDATE books
            SET title = ?, author = ?, publisher = ?,
                category = ?, total_quantity = ?,
                available_quantity = ?
            WHERE book_id = ?
            """;
        try(Connection connection = DBConnection.getConnection()) {
            PreparedStatement st = connection.prepareStatement(query);
            st.setString(1,book.getTitle());
            st.setString(2, book.getAuthor());
            st.setString(3, book.getPublisher());
            st.setString(4, book.getCategory());
            st.setInt(5, book.getTotalQuantity());
            st.setInt(6, book.getAvailableQuantity());
            st.setLong(7, book.getBookId());
            int rows = st.executeUpdate();
            if(rows>0){
                System.out.println("Book updated successfully ");
            }else{
                System.out.println("Book not found!");
            }
        }
    }
    public void deleteBook(Long id){
        try(Connection connection = DBConnection.getConnection()) {
            String query = "DELETE FROM books WHERE book_id=?";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1,id);
            int rows = st.executeUpdate();
            if(rows>0){
                System.out.println("Book Deleted Successfully");
            }else{
                System.out.println("Book not found!");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int checkAvailability(Long id){
        try(Connection connection = DBConnection.getConnection()) {
            String query = "SELECT available_quantity FROM books WHERE book_id=?";
            PreparedStatement st = connection.prepareStatement(query);
            st.setLong(1,id);
            try(ResultSet set = st.executeQuery()) {
                if(set.next()){
                    return set.getInt("available_quantity");
                }
                System.out.println("Book not found!");
                return -1;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
