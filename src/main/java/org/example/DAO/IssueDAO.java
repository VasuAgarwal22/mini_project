
package org.example.DAO;
import org.example.DBConnection;
import org.example.model.IssuedBook;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IssueDAO {

    public void issueBook(Long bookId, Long memberId) {

        String checkBook = """
                SELECT available_quantity FROM books WHERE book_id = ?  FOR UPDATE
                """;

        String checkMember = """
                SELECT member_id
                FROM members
                WHERE member_id = ?
                """;

        String insertIssue = """
                INSERT INTO issued_books
                (book_id, member_id, issue_date, due_date, status)
                VALUES (?, ?, CURRENT_DATE, CURRENT_DATE + 14, 'ISSUED')
                """;

        String updateQuantity = """
                UPDATE books
                SET available_quantity = available_quantity - 1
                WHERE book_id = ?
                """;

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                try (PreparedStatement ps = con.prepareStatement(checkBook)) {
                    ps.setLong(1, bookId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Book not found!");
                        }
                        if (rs.getInt("available_quantity") <= 0) {
                            throw new SQLException("Book not available!");
                        }
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(checkMember)) {
                    ps.setLong(1, memberId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Member not found!");
                        }
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(insertIssue)) {
                    ps.setLong(1, bookId);
                    ps.setLong(2, memberId);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = con.prepareStatement(updateQuantity)) {
                    ps.setLong(1, bookId);
                    ps.executeUpdate();
                }
                con.commit();
                System.out.println("Book issued successfully!");
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void returnBook(Long issueId) {
        String findIssue = """
                SELECT book_id
                FROM issued_books
                WHERE issue_id = ? AND status = 'ISSUED'
                FOR UPDATE
                """;
        String updateIssue = """
                UPDATE issued_books
                SET status = 'RETURNED',
                    return_date = CURRENT_DATE
                WHERE issue_id = ?
                """;
        String updateQuantity = """
                UPDATE books
                SET available_quantity = available_quantity + 1
                WHERE book_id = ?
                """;
        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                Long bookId;
                try (PreparedStatement ps = con.prepareStatement(findIssue)) {
                    ps.setLong(1, issueId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException(
                                    "Issue record not found or book already returned!"
                            );
                        }
                        bookId = rs.getLong("book_id");
                    }
                }
                try (PreparedStatement ps = con.prepareStatement(updateIssue)) {
                    ps.setLong(1, issueId);
                    ps.executeUpdate();
                }
                try (PreparedStatement ps = con.prepareStatement(updateQuantity)) {
                    ps.setLong(1, bookId);
                    ps.executeUpdate();
                }
                con.commit();
                System.out.println("Book returned successfully!");
            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<IssuedBook> viewIssuedBooks() {
        List<IssuedBook> issuedBooks = new ArrayList<>();
        String query = """
                SELECT * FROM issued_books
                WHERE status = 'ISSUED'
                """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                IssuedBook issuedBook = IssuedBook.builder()
                        .issueId(rs.getLong("issue_id"))
                        .bookId(rs.getLong("book_id"))
                        .memberId(rs.getLong("member_id"))
                        .issueDate(rs.getDate("issue_date").toLocalDate())
                        .dueDate(rs.getDate("due_date").toLocalDate())
                        .returnDate(
                                rs.getDate("return_date") == null
                                        ? null
                                        : rs.getDate("return_date").toLocalDate()
                        )
                        .status(rs.getString("status"))
                        .build();
                issuedBooks.add(issuedBook);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issuedBooks;
    }

    public List<IssuedBook> viewIssueHistory(Long memberId) {
        List<IssuedBook> issueHistory = new ArrayList<>();
        String query = """
                SELECT * FROM issued_books
                WHERE member_id = ?
                ORDER BY issue_date DESC
                """;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setLong(1, memberId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    IssuedBook issuedBook = IssuedBook.builder()
                            .issueId(rs.getLong("issue_id"))
                            .bookId(rs.getLong("book_id"))
                            .memberId(rs.getLong("member_id"))
                            .issueDate(rs.getDate("issue_date").toLocalDate())
                            .dueDate(rs.getDate("due_date").toLocalDate())
                            .returnDate(
                                    rs.getDate("return_date") == null
                                            ? null
                                            : rs.getDate("return_date").toLocalDate()
                            )
                            .status(rs.getString("status"))
                            .build();
                    issueHistory.add(issuedBook);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return issueHistory;
    }
}
