# STUDENT RESULT MANAGEMENT SYSTEM USING JAVA & MYSQL

## 1. Title
**Project Title:** Student Result Management System using Java & MySQL  
**Student Name:** Harish Naick  
**Technology:** Java, JDBC, MySQL

---

## 2. Introduction

The **Student Result Management System** is a console-based backend application developed using **Java** and **MySQL**.  
The system helps administrators to manage:

- Department records
- Student records
- Student exam results
- Merit lists and failed student lists
- CSV export of consolidated results

This project focuses on **backend logic, database design, and result processing**, rather than a graphical user interface.

---

## 3. Objectives

- To design a **relational database** for student, department, and result management.
- To implement **CRUD operations** using Java and JDBC.
- To provide **secure admin authentication**.
- To calculate and maintain **subject-wise marks and grades**.
- To generate **reports** such as merit list, toppers, and failed students.
- To enable **exporting results to CSV** for analysis.

---

## 4. System Requirements

### 4.1 Software Requirements

- Operating System: Windows / Linux
- JDK: Java 8 or above
- Database: MySQL
- Editor/IDE: VS Code / IntelliJ / Eclipse
- MySQL Connector/J (JDBC Driver)

### 4.2 Hardware Requirements

- Processor: Intel i3 or above
- RAM: 4 GB or higher
- Storage: 500 MB free space

---

## 5. Technologies Used

- **Java (Core Java)**
  - OOP concepts (Classes, Objects, Encapsulation)
  - Exception Handling
  - Collections & basic I/O

- **JDBC (Java Database Connectivity)**
  - Connecting Java application to MySQL
  - Executing SQL queries using `PreparedStatement`, `Statement`

- **MySQL**
  - Relational database for storing admins, departments, students, and results
  - Use of primary key, foreign key, and unique constraints

- **Design Patterns**
  - DAO (Data Access Object) pattern for separating business logic and database logic

---

## 6. System Design

### 6.1 ER Diagram

```text
+-----------------+         +-----------------+
|    ADMINS       |         |   DEPARTMENTS   |
+-----------------+         +-----------------+
| id (PK)         |         | id (PK)         |
| username        |         | name (UNIQUE)   |
| password        |         +-----------------+
+-----------------+

        +----------------------------------------+
        |                                        |
        v                                        v

+-----------------+         +------------------------------+
|    STUDENTS     |         |           RESULTS            |
+-----------------+         +------------------------------+
| id (PK)         |    +--> | id (PK)                      |
| name            |    |    | student_id (FK -> STUDENTS)  |
| roll_no (UNIQ)  |    |    | subject                      |
| department_id FK+----+    | marks                        |
| year            |         | grade                        |
+-----------------+         +------------------------------+
