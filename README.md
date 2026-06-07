# 🎯 Dual Study Application Tracker

A full-stack web application to track and manage dual study program applications — built as a portfolio project for DHBW Mosbach Computer Science admission.

![Status](https://img.shields.io/badge/status-active-brightgreen) ![Java](https://img.shields.io/badge/Java-17-orange) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-green)

---

## 🖥️ Tech Stack


|
 Layer 
|
 Technology 
|
|
---
|
---
|
|
 Language 
|
 Java 17 
|
|
 Framework 
|
 Spring Boot 3.2 
|
|
 ORM 
|
 Spring Data JPA / Hibernate 6 
|
|
 Database 
|
 SQLite (embedded, zero-config) 
|
|
 Validation 
|
 Jakarta Bean Validation 
|
|
 Build Tool 
|
 Maven 
|
|
 Frontend 
|
 HTML5, Tailwind CSS, Vanilla JavaScript 
|

---

## ✨ Features

- Add, view and delete job applications
- Track status: Applied, Interviewing, Offered, Rejected
- Filter applications by status
- Real-time statistics dashboard
- Responsive dark-mode UI
- RESTful backend with structured error handling

---

## 🏗️ Architecture

3-layer enterprise architecture:

**Controller Layer** → handles HTTP requests and input validation

**Service Layer** → contains all business logic

**Repository Layer** → manages database operations via JPA

**Database** → SQLite file, auto-created on first run

---

## 📊 Data Model

| Field | Type | Constraint |
|---|---|---|
| `id` | Long | Primary Key, Auto-Increment |
| `companyName` | String | Required |
| `positionTitle` | String | Required |
| `status` | Enum | APPLIED / INTERVIEWING / OFFERED / REJECTED |
| `applicationDate` | LocalDate | Auto-set to today if not provided |
| `notes` | String | Optional |

---

## 🔌 API Endpoints

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/applications` | Add a new application |
| `GET` | `/api/applications` | Get all applications |
| `GET` | `/api/applications/status/{status}` | Filter by status |
| `PUT` | `/api/applications/{id}` | Update an application |
| `DELETE` | `/api/applications/{id}` | Delete an application |

---

## 🚀 How to Run

**Prerequisites:** Java 17+, Maven 3.8+

```bash
# 1. Clone the repository
git clone https://github.com/nestorNiloy/dual-study-application-tracker.git

# 2. Start the backend
cd backend
mvn spring-boot:run

# 3. Open the frontend
# Double-click frontend/index.html in File Explorer
```

The server starts at **http://localhost:8080**

---

## 📁 Project Structure

**Backend** (`/backend/src/main/java/com/dhbw/tracker/`)
- `controller/` — REST endpoints
- `service/` — Business logic
- `repository/` — Database layer
- `model/` — Data models
- `exception/` — Error handling

**Frontend** (`/frontend/`)
- `index.html` — Full dashboard UI

---

## ⚠️ Error Handling

All errors return structured JSON — no raw stack traces:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Application not found with id: 99",
  "timestamp": "2024-06-04T14:30:00",
  "path": "/api/applications/99"
}
```
