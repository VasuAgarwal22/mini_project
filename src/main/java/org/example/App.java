package org.example;

import org.example.DAO.BookDAO;
import org.example.DAO.IssueDAO;
import org.example.DAO.MemberDAO;
import org.example.model.Book;
import org.example.model.Member;

import java.sql.SQLException;
import java.util.Scanner;

public class App
{
    public static void main( String[] args ) throws SQLException {
        Scanner sc = new Scanner(System.in);
        BookDAO bookDAO = new BookDAO();
        MemberDAO memberDAO = new MemberDAO();
        IssueDAO issueDAO = new IssueDAO();
        DBInitializer.initializeDatabase();
        while (true) {
            System.out.println("\n========== LIBRARY MANAGEMENT SYSTEM ==========");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book By ID");
            System.out.println("4. Update Book");
            System.out.println("5. Delete Book");
            System.out.println("6. Check Book Availability");
            System.out.println("7. Add Member");
            System.out.println("8. View All Members");
            System.out.println("9. Search Member By ID");
            System.out.println("10. Update Member");
            System.out.println("11. Delete Member");
            System.out.println("12. Issue Book");
            System.out.println("13. Return Book");
            System.out.println("14. View Issued Books");
            System.out.println("15. View Member Issue History");
            System.out.println("0. Exit");

            System.out.println("Enter your choice !!");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> {
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter publisher: ");
                    String publisher = sc.nextLine();
                    System.out.print("Enter category: ");
                    String category = sc.nextLine();
                    System.out.print("Enter total quantity: ");
                    int total = sc.nextInt();
                    System.out.print("Enter available quantity: ");
                    int available = sc.nextInt();

                    Book book = Book.builder()
                            .title(title)
                            .author(author)
                            .publisher(publisher)
                            .category(category)
                            .totalQuantity(total)
                            .availableQuantity(available)
                            .build();
                    bookDAO.addBook(book);
                }
                case 2 -> bookDAO.viewAllBooks();
                case 3 -> {
                    System.out.println("Enter book ID");
                    Long id = sc.nextLong();
                    Book book = bookDAO.searchBookById(id);
                    System.out.println(book != null ? book : "Book not found!");
                }
                case 4 -> {
                    System.out.println("Enter book Id : ");
                    Long id = sc.nextLong();
                    sc.nextLine();
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter author: ");
                    String author = sc.nextLine();

                    System.out.print("Enter publisher: ");
                    String publisher = sc.nextLine();

                    System.out.print("Enter category: ");
                    String category = sc.nextLine();

                    System.out.print("Enter total quantity: ");
                    int total = sc.nextInt();

                    System.out.print("Enter available quantity: ");
                    int available = sc.nextInt();

                    Book book = Book.builder()
                            .bookId(id)
                            .title(title)
                            .author(author)
                            .publisher(publisher)
                            .category(category)
                            .totalQuantity(total)
                            .availableQuantity(available)
                            .build();

                    bookDAO.updateBook(book);
                }

                case 5 -> {
                    System.out.print("Enter book ID: ");
                    bookDAO.deleteBook(sc.nextLong());
                }

                case 6 -> {
                    System.out.print("Enter book ID: ");
                    Long id = sc.nextLong();

                    int available = bookDAO.checkAvailability(id);

                    if (available >= 0) {
                        System.out.println("Available copies: " + available);
                    }
                }

                case 7 -> {
                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter department: ");
                    String department = sc.nextLine();

                    Member member = Member.builder()
                            .name(name)
                            .email(email)
                            .phone(phone)
                            .department(department)
                            .build();

                    memberDAO.addMember(member);
                }

                case 8 -> memberDAO.viewAllMembers();

                case 9 -> {
                    System.out.print("Enter member ID: ");
                    Long id = sc.nextLong();

                    Member member = memberDAO.searchMemberById(id);

                    System.out.println(member != null ? member : "Member not found!");
                }

                case 10 -> {
                    System.out.print("Enter member ID: ");
                    Long id = sc.nextLong();
                    sc.nextLine();

                    System.out.print("Enter name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine();

                    System.out.print("Enter department: ");
                    String department = sc.nextLine();

                    Member member = Member.builder()
                            .memberId(id)
                            .name(name)
                            .email(email)
                            .phone(phone)
                            .department(department)
                            .build();

                    memberDAO.updateMember(member);
                }

                case 11 -> {
                    System.out.print("Enter member ID: ");
                    memberDAO.deleteMember(sc.nextLong());
                }

                case 12 -> {
                    System.out.print("Enter book ID: ");
                    Long bookId = sc.nextLong();

                    System.out.print("Enter member ID: ");
                    Long memberId = sc.nextLong();

                    issueDAO.issueBook(bookId, memberId);
                }

                case 13 -> {
                    System.out.print("Enter issue ID: ");
                    issueDAO.returnBook(sc.nextLong());
                }

                case 14 -> issueDAO.viewIssuedBooks().forEach(System.out::println);

                case 15 -> {
                    System.out.print("Enter member ID: ");
                    Long id = sc.nextLong();
                    issueDAO.viewIssueHistory(id).forEach(System.out::println);
                }
                case 0 -> {
                    System.out.println("Thank you for using Library Management System!");
                    sc.close();
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }
}

