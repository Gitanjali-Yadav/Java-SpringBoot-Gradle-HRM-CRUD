# HRM CRUD Application

A Human Resource Management (HRM) REST API built with **Spring Boot 4.1**, **Java 25**, and **Gradle**, demonstrating full CRUD (Create, Read, Update, Delete) operations for managing employee records — built and deployed end-to-end on AWS.

🟢 **Live and deployed** — this application is running on AWS Elastic Beanstalk, backed by a MySQL database on AWS RDS.

---

## 🔗 Live Demo

**Swagger UI (interactive API docs):**
```
http://hrmcrudawsdemo-env.eba-mjwaj3mk.eu-north-1.elasticbeanstalk.com/swagger-ui/index.html
```

Open this link in a browser to explore and test all endpoints directly — no setup required.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 25 |
| Framework | Spring Boot 4.1.0 |
| Data Access | Spring Data JPA (Hibernate ORM) |
| Database | MySQL 8 (AWS RDS) |
| Build Tool | Gradle |
| API Docs | springdoc-openapi (Swagger UI) |
| Boilerplate Reduction | Lombok |
| Hosting | AWS Elastic Beanstalk (Java/Corretto platform) |
| IDE | IntelliJ IDEA |

---

## Project Structure

```
src/main/java/com/fullstack/
├── HrmcrudApplication.java   # Main application entry point
├── model/                    # JPA entity classes (Employee, etc.)
├── repository/                # Spring Data JPA repositories
├── service/                   # Business logic layer
├── controller/                 # REST API controllers
└── exception/                  # Custom exception handling
```

---

## API Endpoints

Base path: `/employees`

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST   | `/employees/save` | Create a new employee |
| GET    | `/employees/getAll` | Get all employees |
| GET    | `/employees/getEmpByID/{empId}` | Get a single employee by ID |
| PUT    | `/employees/update/{empId}` | Update an existing employee |
| DELETE | `/employees/deleteById/{empId}` | Delete an employee by ID |

### Sample request — Create employee

```bash
curl -X POST http://hrmcrudawsdemo-env.eba-mjwaj3mk.eu-north-1.elasticbeanstalk.com/employees/save \
  -H "Content-Type: application/json" \
  -d '{
    "empName": "Gitanjali Yadav",
    "empSalary": 98000.1
  }'
```

(Replace the base URL with `http://localhost:8080` if running locally.)

---

## Running Locally

### Prerequisites

- JDK 25
- MySQL Server (running locally)
- IntelliJ IDEA (or any IDE with Gradle support)
- Git

### 1. Clone the repository

```bash
git clone https://github.com/Gitanjali-Yadav/Java-SpringBoot-Gradle-HRM-CRUD.git
cd Java-SpringBoot-Gradle-HRM-CRUD
```

### 2. Create the local database

```sql
CREATE DATABASE hrmcrud;
```

(Optional — the app is configured with `createDatabaseIfNotExist=true` and will create it automatically on first run.)

### 3. Configure `application.properties`

Open `src/main/resources/application.properties` and set your local MySQL credentials:

```properties
spring.application.name=hrmcrud

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/hrmcrud?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=your_local_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Run the app

From IntelliJ: right-click `HrmcrudApplication.java` → **Run**

Or via terminal:
```bash
./gradlew bootRun
```

The app starts on **http://localhost:8080**. Swagger UI is available at:
```
http://localhost:8080/swagger-ui/index.html
```

---

## Deployment (AWS)

This project is deployed using **AWS Elastic Beanstalk** for hosting and **AWS RDS** for the database — no local server required to run it.

### Architecture

```
Browser / API client
        │
        ▼
AWS Elastic Beanstalk (Java/Corretto, EC2 instance)
   — runs the Spring Boot app as a bootJar
        │
        ▼
AWS RDS (MySQL 8) — eu-north-1 (Stockholm)
```

### How it was deployed

1. **Built a runnable jar:**
   ```bash
   ./gradlew bootJar
   ```
   Output: `build/libs/hrmcrud-0.0.1-SNAPSHOT.jar`

2. **Configured the app to run on port 5000** — Elastic Beanstalk's Java SE platform expects the app on this port by default:
   ```properties
   server.port=5000
   ```

3. **Created an AWS RDS MySQL instance** (`hrmcrud` database, eu-north-1 region) and updated `application.properties` with the RDS endpoint:
   ```properties
   spring.datasource.url=jdbc:mysql://<rds-endpoint>:3306/hrmcrud?createDatabaseIfNotExist=true
   ```

4. **Opened network access** — added an inbound rule on the RDS security group allowing MySQL/Aurora traffic (port 3306) from the Elastic Beanstalk EC2 instance's security group, so the deployed app can reach the database.

5. **Created an Elastic Beanstalk environment:**
    - Platform: Java, Corretto 25 running on Amazon Linux 2023
    - Application code: uploaded the built jar directly
    - IAM: created a Beanstalk service role and used the default EC2 instance profile

6. **Deployed** — Elastic Beanstalk provisions the EC2 instance, uploads the jar, and starts the app automatically.

### Redeploying updates

Whenever the code changes:

```bash
./gradlew bootJar
```

Then in the Elastic Beanstalk console: **Environment → Upload and deploy** → select the new jar from `build/libs/` → **Deploy**.

---

## Testing the Live API

You can test the deployed application in two ways:

### Option 1: Swagger UI (easiest, no tools needed)

Open in a browser:
```
http://hrmcrudawsdemo-env.eba-mjwaj3mk.eu-north-1.elasticbeanstalk.com/swagger-ui/index.html
```
Expand any endpoint → **Try it out** → fill in parameters → **Execute**. Swagger UI shows the request, response body, and status code directly.

### Option 2: curl / Postman

Example — fetch all employees:
```bash
curl http://hrmcrudawsdemo-env.eba-mjwaj3mk.eu-north-1.elasticbeanstalk.com/employees/getAll
```

Example — create an employee:
```bash
curl -X POST http://hrmcrudawsdemo-env.eba-mjwaj3mk.eu-north-1.elasticbeanstalk.com/employees/save \
  -H "Content-Type: application/json" \
  -d '{"empName": "Test User", "empSalary": 50000}'
```

---

## ⚠️ Security Notes (known items to improve)

This project was built as a learning exercise, and a few things are intentionally simplified for now but worth knowing:

- **Database credentials** are currently set directly in `application.properties`. For a production setup, these should be moved to environment variables (Elastic Beanstalk → Configuration → Software → Environment properties) or AWS Secrets Manager instead of being committed to source control.
- **RDS security group** currently allows broader inbound access than ideal for a long-term production setup — intended to be narrowed to only the Beanstalk environment's security group going forward.
- **No authentication/authorization** is implemented yet — all endpoints are publicly accessible. Not suitable for real employee data as-is.

---

## Status

🚧 Work in progress — built as a hands-on learning project covering the full journey from local Spring Boot development to a live AWS deployment. Next steps: externalize secrets, add authentication, add input validation.

## License

This project is for educational purposes.