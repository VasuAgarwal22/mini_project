# Library Management System

A console-based **Library Management System** developed using Java, JDBC, and PostgreSQL. This project allows users to manage books, members, and book issuing/returning operations through a menu-driven interface.

## Features

### Book Management
- Add new books to the library.
- View all available books.
- Search books by ID.
- Update book details.
- Delete books.
- Check book availability.

### Member Management
- Register new members.
- View all registered members.
- Search members by ID.
- Update member details.
- Delete members.

### Issue and Return Management
- Issue books to registered members.
- Return issued books.
- Automatically update available book quantity.
- View currently issued books.
- View issue history of a member.
- Automatically calculate the due date (14 days after issue).

## Tech Stack

- **Programming Language:** Java
- **Database:** PostgreSQL
- **Database Connectivity:** JDBC
- **Build Tool:** Maven
- **IDE:** IntelliJ IDEA
- **Libraries:** Lombok, PostgreSQL JDBC Driver

## Project Structure

```text
LibraryManagementSystem/
│
├── src/
│   └── main/
│       └── java/
│           └── org/
│               └── example/
│                   ├── DAO/
│                   │   ├── BookDAO.java
│                   │   ├── MemberDAO.java
│                   │   └── IssueDAO.java
│                   │
│                   ├── model/
│                   │   ├── Book.java
│                   │   ├── Member.java
│                   │   └── IssuedBook.java
│                   │
│                   ├── DBConnection.java
│                   ├── DBInitializer.java
│                   └── App.java
│
├── pom.xml
└── README.md
```

## Database Design

The application uses three main tables:

**1. books**
- `book_id` – Primary Key
- `title`
- `author`
- `publisher`
- `category`
- `total_quantity`
- `available_quantity`

**2. members**
- `member_id` – Primary Key
- `name`
- `email` – Unique
- `phone`
- `department`

**3. issued_books**
- `issue_id` – Primary Key
- `book_id` – Foreign Key
- `member_id` – Foreign Key
- `issue_date`
- `due_date`
- `return_date`
- `status`

## Setup and Installation

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/LibraryManagementSystem.git
```

### 2. Configure PostgreSQL

- Install PostgreSQL.
- Create a database named `library_management`.
- Update the database username and password in `DBConnection.java`.
- Ensure PostgreSQL is running on port `5432`.

### 3. Install Dependencies

Maven dependencies are configured in `pom.xml`. Reload the Maven project to download them.

### 4. Run the Application

Run the `App.java` file from your IDE.

The database tables are initialized automatically when the application starts.

## Concepts Implemented

- Object-Oriented Programming (OOP)
- Data Access Object (DAO) Design Pattern
- JDBC Connectivity
- PreparedStatement and ResultSet
- CRUD Operations
- Exception Handling
- SQL Constraints and Foreign Keys
- Database Transactions
- Transaction Management using Commit and Rollback
- Lombok Builder Pattern

## Future Improvements

- Add a graphical user interface.
- Implement login and authentication.
- Add book search by title, author, and category.
- Implement overdue fine calculation.
- Generate library reports.

## Author

**Vasu Agarwal**

B.Tech – Computer Science and Engineering

ABES Engineering College

---

*This project was developed as a mini project to practice Java, JDBC, PostgreSQL, and database management concepts.*
