## Banking Backend REST API

- user registration & login (with JWT token generation)
- account creation for authenticated users
- money transfers between accounts

### Technologies

- Java 21
- Maven
- Spring Boot
- JWT
- PostgreSQL

((see dependencies in `pom.xml`))

### Frontends Using This API:

- [**React Web App**](https://github.com/kenryhraval/banking-web)

- [**JavaFX Desktop App**](https://github.com/kenryhraval/banking-desktop)
 
## How to Run

1. Clone the repo:
```bash
git clone https://github.com/yourusername/banking-api.git
cd banking-api
```
2. Run the backend:
```bash
./mvnw spring-boot:run
```

Swagger UI & interactive documentation: http://localhost:8080/swagger-ui/index.html
