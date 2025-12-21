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

# PageHelper AOP 동적 페이징 가이드

## 주요 특징

1. **자동 페이징**: `@PageableQuery` 어노테이션만 추가하면 자동으로 페이징 적용
2. **코드 간소화**: `PageHelper.startPage()` 수동 호출 불필요
3. **동적 검색**: MyBatis 동적 SQL과 완벽 통합
4. **보안**: SQL Injection 방지를 위한 정렬 컬럼 검증
5. **유연성**: 메서드별로 다른 페이징 설정 가능

## 빠른 시작

### 1. 의존성 추가 (pom.xml)

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
<dependency>
    <groupId>com.github.pagehelper</groupId>
    <artifactId>pagehelper-spring-boot-starter</artifactId>
    <version>2.1.0</version>
</dependency>
```

### 2. DTO에 Pageable 인터페이스 구현

```java
@Data
public class UserSearchDTO implements Pageable {
    // 검색 조건
    private String username;

    // 페이징 파라미터
    private Integer pageNum;
    private Integer pageSize;
}
```

### 3. 서비스 메서드에 어노테이션 추가

```java
@PageableQuery(defaultPageNum = 1, defaultPageSize = 10, maxPageSize = 100)
@Transactional(readOnly = true)
public PageInfo<User> getUserList(UserSearchDTO searchDTO) {
    List<User> users = userMapper.selectUsers(searchDTO);
    return new PageInfo<>(users);
}
```

### 4. API 호출

```bash
# 기본 페이징 (1페이지, 10개)
GET /api/users

# 커스텀 페이징 (2페이지, 20개)
GET /api/users?pageNum=2&pageSize=20

# 검색 + 페이징
GET /api/users?username=john&pageNum=1&pageSize=10
```

## 고급 사용법

### 정렬 포함 페이징

```java
// DTO에 PageableWithSort 구현
@Data
public class UserSearchDTO implements PageableWithSort {
    private Integer pageNum;
    private Integer pageSize;
    private String orderBy;
    private String orderDirection;
}

// 서비스 메서드
@PageableQueryWithSort(
    defaultPageNum = 1,
    defaultPageSize = 20,
    defaultOrderBy = "created_at",
    defaultOrderDirection = "DESC",
    allowedOrderColumns = {"id", "username", "email", "age"}
)
@Transactional(readOnly = true)
public PageInfo<User> getUsersWithSort(UserSearchDTO searchDTO) {
    List<User> users = userMapper.selectUsers(searchDTO);
    return new PageInfo<>(users);
}
```

### API 호출 예시

```bash
# 나이 내림차순 정렬
GET /api/users?pageNum=1&pageSize=20&orderBy=age&orderDirection=DESC

# 이름 오름차순 정렬
GET /api/users?orderBy=username&orderDirection=ASC
```

## 응답 예시

```json
{
  "pageNum": 1,
  "pageSize": 10,
  "size": 10,
  "total": 45,
  "pages": 5,
  "list": [
    {
      "id": 1,
      "username": "john_doe",
      "email": "john@example.com",
      "age": 25
    }
  ],
  "isFirstPage": true,
  "isLastPage": false,
  "hasPreviousPage": false,
  "hasNextPage": true,
  "prePage": 0,
  "nextPage": 2
}
```

## 주의사항

1. **@Order(1)**: PageHelperAspect는 트랜잭션보다 먼저 실행되어야 합니다
2. **ThreadLocal 정리**: PageHelper.clearPage()가 자동으로 호출됩니다
3. **SQL Injection 방지**: 정렬 컬럼은 반드시 검증됩니다
4. **최대 페이지 크기**: maxPageSize를 초과하면 자동으로 제한됩니다

## 성능 최적화 팁

1. 적절한 인덱스 생성
2. COUNT 쿼리 최적화
3. 합리적인 maxPageSize 설정 (기본: 100)
4. 필요한 컬럼만 SELECT

## 트러블슈팅

### 페이징이 적용되지 않는 경우

- @PageableQuery 어노테이션 확인
- Pageable 인터페이스 구현 확인
- AOP 설정 활성화 확인

### COUNT 쿼리가 2번 실행되는 경우

- PageInfo 생성이 중복되지 않도록 확인
- PageHelper.clearPage() 호출 시점 확인

## 더 알아보기

- PageHelper 공식 문서: https://pagehelper.github.io/
- Spring AOP 문서: https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop
