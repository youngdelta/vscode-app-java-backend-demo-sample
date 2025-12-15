# Spring Boot MyBatis Pagination Demo

This project is a Spring Boot application demonstrating various pagination strategies using MyBatis and PageHelper. It provides examples of both Server-Side Rendering (Thymeleaf) and Client-Side Rendering (AJAX).

## 🚀 Application Features

- **Framework**: Spring Boot 3.5.8
- **Persistence**: MyBatis 3.0.5, MySQL Connector
- **Pagination**: [PageHelper](https://github.com/pagehelper/Mybatis-PageHelper)
- **Template Engine**: Thymeleaf
- **Tooling**: Lombok, P6Spy (SQL Logging), DevTools

## 📚 Pagination Strategies Implemented

The application demonstrates three distinct ways to handle pagination with MyBatis:

1.  **AOP PageHelper**: Automatic pagination using Aspect Oriented Programming to intercept methods and apply `PageHelper.startPage()`.
2.  **Argument Resolver**: Explicit pagination control via Controller arguments (`PageRequest`).
3.  **RowBounds**: MyBatis built-in `RowBounds` for logical pagination (Note: Not recommended for large datasets).

## 🛠️ Setup & Running

### Prerequisites

- Java 17+
- Maven
- MySQL Database

### Database Configuration

1.  Ensure you have a MySQL instance running.
2.  Create a schema named `world` (or update `application.properties`).
3.  Update `src/main/resources/application.properties` with your credentials:
    ```properties
    spring.datasource.url=jdbc:p6spy:mysql://localhost:3306/world?serverTimezone=UTC
    spring.datasource.username=user1
    spring.datasource.password=your_password
    ```

### Running the App

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

## 🔍 Key Endpoints

| URL               | Description                                                                                                                   |
| :---------------- | :---------------------------------------------------------------------------------------------------------------------------- |
| `/users/view`     | **Server-Side Rendering**: Pagination demo using Thymeleaf. Compares AOP, ArgumentResolver, vs RowBounds results on one page. |
| `/users/userAjax` | **Client-Side Rendering**: AJAX-based pagination.                                                                             |
| `/users/viewAjax` | **Client-Side Rendering**: Alternative view.                                                                                  |
| `/users/ajax`     | **API**: Returns paginated User data in JSON format for the frontend.                                                         |

## 📂 Project Structure

- `src/main/java/web/example/app/`
  - `city/controller/UserController.java`: Main controller handling pagination requests.
  - `city/mapper/`: MyBatis mappers.
  - `config/aop/PageHelperAspect.java`: AOP Logic for automatic pagination.
- `src/main/resources/`
  - `mapper/`: MyBatis XML mappers.
  - `templates/`: Thymeleaf views (`userPagingView.html`, `userAjax.html`, etc.).
  - `application.properties`: Configuration.

## 📝 Notes

- **SQL Logging**: P6Spy is enabled to log the actual SQL executed with parameters, useful for debugging pagination queries.
- **Thymeleaf**: configured to use disconnected templates in development.
