# E-Commerce Product Management System

## Overview

The E-Commerce Product Management System is designed to facilitate robust product and category management using Spring Boot. It leverages Thymeleaf for dynamic rendering of HTML pages and Bootstrap for styling. The system supports efficient request handling, interaction with a MySQL database via Spring Data JPA, and offers both public and admin API endpoints.

## Features

- **Spring Boot Integration**: Utilizes Spring Boot for streamlined project setup and configuration.
- **Database Interaction**: Employs Spring Data JPA for CRUD operations and pagination.
- **Web Interface**: Uses Thymeleaf for server-side rendering of HTML and Bootstrap for responsive design.
- **API Endpoints**: Provides separate public and admin endpoints for accessing and managing products and categories.
- **Pagination**: Implements pagination for listing products.

## Project Structure

```
src/
|-- main/
|   |-- java/
|   |   |-- com.example.product_management/
|   |   |   |-- controller/
|   |   |   |-- implement/
|   |   |   |-- model/
|   |   |   |-- repository/
|   |   |   |-- service/
|   |   |-- resources/
|   |   |   |-- templates/
|   |   |   |-- static/
|-- test/
```

## Technologies Used

- **Spring Boot**: For building and running the application.
- **Spring Data JPA**: For database interactions.
- **Thymeleaf**: For dynamic HTML rendering.
- **Bootstrap**: For responsive and modern styling.
- **MySQL**: As the relational database management system.

## Endpoints

### Public Endpoints

- **Get all categories**: `GET /api/public/categories`
- **Get all products**: `GET /api/public/products`

### Admin Endpoints

- **Get all categories**: `GET /api/admin/categories`
- **Get all products**: `GET /api/admin/products`
- **Create a category**: `POST /api/admin/categories`
- **Create a product**: `POST /api/admin/products`
- **Update a category**: `PUT /api/admin/categories/{categoryId}`
- **Update a product**: `PUT /api/admin/products/{productId}`
- **Delete a category**: `DELETE /api/admin/categories/{categoryId}`
- **Delete a product**: `DELETE /api/admin/products/{productId}`

## Pagination

The system supports pagination for product listings, allowing users to navigate through products in a paginated manner.

## How to Run

1. **Clone the Repository**:


2. **Configure the Database**:
   Update the `application.properties` file with your MySQL database credentials.
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/your_database
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=update
    ```

3. **Build and Run the Application**:
    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4. **Access the Application**:
    - **Public Access**: Navigate to `http://localhost:8080/` for the public interface.
    - **Admin Access**: Navigate to `http://localhost:8080/admin` for the admin interface.

## Usage

### Adding Products and Categories

1. **Admin Interface**:
    - Use the admin interface to add, update, and delete products and categories.
    - Navigate to `http://localhost:8080/admin` to access the admin functionalities.

2. **Public Interface**:
    - Users can view products and categories without logging in.
    - Navigate to `http://localhost:8080/` to view available products and categories.

### Searching for Products

- Use the search bar on the public interface to search for products by name. The search is case-insensitive.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature/your-feature`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to the branch (`git push origin feature/your-feature`).
6. Create a new Pull Request.


### Video Demonstration on [Loom](https://www.loom.com/share/596e6d69325745ddaf02eb3f8c2ccbcb?sid=77780225-4e5f-43f5-8479-80835801b241)