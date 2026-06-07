# Dual-Study Application Tracker — REST API

A backend REST API built with **Java 17 + Spring Boot 3.x + SQLite** to track the status of dual-study (Duales Studium) job applications. Built as a portfolio project for DHBW Mosbach Computer Science admission.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| ORM | Spring Data JPA / Hibernate 6 |
| Database | SQLite (embedded, zero-config) |
| Validation | Jakarta Bean Validation |
| Build Tool | Maven |

---

## Architecture

The project follows a standard 3-layer enterprise architecture:

```
[ Postman / Client ]
        │  HTTP Requests
        ▼
┌────────────────────────────────────┐
│  Controller Layer                  │  ← HTTP routing, @Valid input guard
│  ApplicationController.java        │
├────────────────────────────────────┤
│  Service Layer                     │  ← Business logic, exception throwing
│  ApplicationService (interface)    │
│  ApplicationServiceImpl            │
├────────────────────────────────────┤
│  Repository Layer                  │  ← JpaRepository + derived queries
│  ApplicationRepository.java        │
└──────────────┬─────────────────────┘
               │
               ▼
      [ applications.db ]   ← SQLite file, auto-created on first run
```

---

## Data Model

| Field | Type | Constraint |
|---|---|---|
| `id` | Long | Primary Key, Auto-Increment |
| `companyName` | String | Required |
| `positionTitle` | String | Required |
| `status` | Enum | Required — APPLIED / INTERVIEWING / REJECTED / OFFERED |
| `applicationDate` | LocalDate | Auto-set to today if not provided |
| `notes` | String | Optional |

---

## API Endpoints

| Method | Path | Body | Status | Description |
|---|---|---|---|---|
| `POST` | `/api/applications` | JSON | 201 | Add a new application |
| `GET` | `/api/applications` | — | 200 | Get all applications |
| `GET` | `/api/applications/status/{status}` | — | 200 | Filter by status |
| `PUT` | `/api/applications/{id}` | JSON | 200 | Update an application |
| `DELETE` | `/api/applications/{id}` | — | 204 | Delete an application |

---

## How to Run

**Prerequisites:** Java 17+, Maven 3.8+

```bash
# 1. Clone the repository
git clone <your-repo-url>
cd application-tracker

# 2. Build the project
mvn clean install

# 3. Run the application
mvn spring-boot:run
```

The server starts at **http://localhost:8080**
SQLite database file `applications.db` is auto-created in the project root.

---

## Example Requests (Postman / curl)

**Create a new application:**
```bash
curl -X POST http://localhost:8080/api/applications \
  -H "Content-Type: application/json" \
  -d '{
    "companyName": "SAP SE",
    "positionTitle": "Dual Student - Software Engineering",
    "status": "APPLIED",
    "notes": "Applied via company website. Contact: hr@sap.com"
  }'
```

**Get all applications:**
```bash
curl http://localhost:8080/api/applications
```

**Filter by status:**
```bash
curl http://localhost:8080/api/applications/status/INTERVIEWING
```

**Update an application:**
```bash
curl -X PUT http://localhost:8080/api/applications/1 \
  -H "Content-Type: application/json" \
  -d '{
    "companyName": "SAP SE",
    "positionTitle": "Dual Student - Software Engineering",
    "status": "INTERVIEWING",
    "applicationDate": "2024-06-01",
    "notes": "Interview scheduled for June 15th at 10:00 AM"
  }'
```

**Delete an application:**
```bash
curl -X DELETE http://localhost:8080/api/applications/1
```

---

## Error Handling

All errors return a structured JSON envelope — no raw stack traces:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Application not found with id: 99",
  "timestamp": "2024-06-04T14:30:00",
  "path": "/api/applications/99"
}
```

---

## Project Structure

```
src/
├── main/
│   ├── java/com/dhbw/tracker/
│   │   ├── ApplicationTrackerApplication.java
│   │   ├── controller/
│   │   │   └── ApplicationController.java
│   │   ├── exception/
│   │   │   ├── ErrorResponse.java
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   └── ResourceNotFoundException.java
│   │   ├── model/
│   │   │   ├── Application.java
│   │   │   └── ApplicationStatus.java
│   │   ├── repository/
│   │   │   └── ApplicationRepository.java
│   │   └── service/
│   │       ├── ApplicationService.java
│   │       └── ApplicationServiceImpl.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/dhbw/tracker/
        └── ApplicationTrackerApplicationTests.java
```
