# Bookstore API

A Spring Boot REST API for managing books, authors, customers, shelves, and sales in a bookstore.

The project implements authentication (JWT-based), authorization (role-based access), and database migrations (Liquibase).  
It follows clean code architecture with DTOs, Services, Repositories, and Controllers.

---

## Features

- Add, update, delete, and fetch books
- Sell books and track sales
- Manage customers and authors
- Upload books via CSV file
- Group books by publication year
- Fetch a random cat fact (public endpoint)
- Role-based access control: **ADMIN** and **FINANCE**
- Centralized exception handling
- JWT authentication with refresh tokens
- Data validation and custom error responses
- External API integration (Cat Facts)
- Full database migration management using Liquibase

---

## Project Structure

```bash
bookstore
├── docs/
│   └── Bookstore_er_diagram.png # Entity-Relationship diagram
├── src/main/java/com/bookstore/bookstore/
│   ├── common/                 # Utilities, Enums, Global Exception Handler
│   ├── config/                 # Security and App configurations
│   ├── controller/             # REST Controllers
│   ├── dto/                    # Data Transfer Objects
│   ├── entity/                 # JPA Entities
│   ├── exception/              # Custom exceptions
│   ├── mapper/                 # Entity <-> DTO mappers
│   ├── repository/             # Spring Data JPA Repositories
│   └── service/                # Business Logic (Services)
├── src/main/resources/
│   ├── application.properties  # App configuration
│   ├── db/changelog/           # Liquibase migration files
│   └── data.sql                # Initial data insertion
├── pom.xml                      # Maven project configuration
└── README.md                    # (You're here!)
```

---

## Technologies Used

- **Java 21**
- **Spring Boot 3.3.5**
- **Spring Security** (with JWT Authentication)
- **Spring Data JPA**
- **Liquibase**
- **MySQL**
- **Lombok**
- **OpenCSV** (CSV import)
- **MapStruct** (DTO mapping)
- **Javadoc** (method documentation)
- **Swagger / OpenAPI** (API documentation)

---

## Database Schema

All tables and relationships are created using Liquibase changelogs.

*   Snake_case naming convention for tables and columns.

*   ER Diagram located at: docs/Bookstore\_er\_diagram.png


Database tables:

- book
- author
- customer
- sale
- shelf
- user
- access_token
- refresh_token
- and relationship tables like book\_authors, book\_shelves.

---

## How to Run the Project

1.  **Clone the Repository**

    ```bash
    git clone https://github.com/yourusername/bookstore.git
    cd bookstore
    ```

2. **Set Up Database**

   Make sure you have MySQL running and create a database:

    ```sql
    CREATE DATABASE bookstore;
    ```

   Update the database credentials in `src/main/resources/application.properties`:

    ```properties
    spring.datasource.username=your_mysql_username
    spring.datasource.password=your_mysql_password
    ```

3. **Build and Run**

    ```bash
    ./mvnw spring-boot:run
    ```

   Liquibase will automatically create tables and insert sample data.

4. **Access Swagger API Documentation**

   Visit:

    ```bash
    http://localhost:8080/swagger-ui/index.html
    ```

---

## Authentication & Authorization

Authenticate via `/api/auth/login` to receive an **Access Token** and **Refresh Token**.

Use the **Access Token** as a Bearer token in the `Authorization` header for secured APIs.

### Roles

- **ADMIN**: Full access to book/customer/author management.
- **FINANCE**: Can sell books and access financial reports.

### Default Users (inserted via `data.sql`)

| Username | Password    | Role    |
|:---------|:------------|:--------|
| admin    | password123 | ADMIN   |
| finance  | password123 | FINANCE |

> **Note:**  
> Passwords are securely stored in hashed format.

---

## APIs Overview

| Feature                         | Endpoint                    | Access           |
|:--------------------------------|:----------------------------|:-----------------|
| Add/Update/Delete/Fetch Books   | `/api/book`                 | ADMIN            |
| Sell a Book                     | `/api/sale/create`          | FINANCE          |
| Top Sales / Revenue Reports     | `/api/sale`                 | ADMIN & FINANCE  |
| Manage Authors                  | `/api/author`               | ADMIN            |
| Manage Customers                | `/api/customer`             | ADMIN            |
| Upload Books via CSV            | `/api/book/import`          | ADMIN            |
| Cat Fact                        | `/api/cat/get-cat-fact`     | Public           |

See full API documentation at [`/swagger-ui/index.html`](http://localhost:8080/swagger-ui/index.html).

---

## Error Handling

All errors are returned in a consistent format:

```json
{
  "status": "error",
  "message": "Resource not found",
  "timestamp": "2025-04-28T14:23:12.345Z"
}
```

### Custom Exceptions

- `BookNotFoundException`
- `BookAlreadyExistsException`
- `CustomerNotFoundException`
- `InvalidCredentialsException`
- `InvalidFileException`
- `CatFactException`

---

## Bonus: Cat Fact API

**Endpoint:** `/api/cat/get-cat-fact`

- Calls external API for random cat facts.
- If the external API doesn't respond within 10 seconds, returns:

> "If you poke the tail of a sleeping cat, it will respond accordingly."

---

## Future Improvements

- Add unit tests and integration tests.
- Implement refresh token logic for longer sessions.
- Containerize with Docker.
- CI/CD pipeline (GitHub Actions / Jenkins).

---

## Author

**Marko Milunović**  
[LinkedIn Profile](https://www.linkedin.com/in/marko-milunović-946428267)

---

## License

This project is licensed under the **MIT License**.  
Feel free to use it for learning purposes or as a boilerplate for other applications.