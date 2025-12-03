# 🎓 Student Result Management System using Java & MySQL

A console-based backend application to manage student records and results using **Java**, **JDBC**, and **MySQL**.  
This project focuses on **clean backend logic, database design, and result processing** rather than a graphical UI.

---

## 📌 Features

- 🔐 **Admin Login**
  - Admin-only access using credentials stored in the database.
- 🏫 **Department Management**
  - Add and view departments (e.g., CSE, ECE, MECH).
- 👨‍🎓 **Student Management**
  - Add, view, update, and delete student records.
- 📚 **Result Management**
  - Add subject-wise marks for each student.
  - Automatic grade calculation based on marks.
- 🏅 **Analytics & Reports**
  - Merit list (sorted by average marks).
  - Top N students.
  - List of failed students based on pass marks.
- 📤 **CSV Export**
  - Export all student results to a CSV file for further analysis in Excel/Sheets.

---

## 🛠 Tech Stack

**Language & Platform**
- ☕ Java (Core Java, OOP, Exception Handling)

**Database & Connectivity**
- 🐬 MySQL
- 🔗 JDBC (Java Database Connectivity)
- 📦 MySQL Connector/J (JDBC Driver)

**Architecture & Concepts**
- 🧱 DAO (Data Access Object) Pattern  
- ✅ Exception Handling (SQL + Runtime)  
- 🧮 SQL (DDL, DML, Joins, Aggregations)

---

## 📂 Project Structure

```text
StudentResultSystem/
│
├── AdminDAO.java
├── DBUtil.java
├── DepartmentDAO.java
├── ResultDAO.java
├── Student.java
├── StudentDAO.java
├── Main.java
│
├── schema.sql          # Database creation script (optional file you add)
├── PROJECT_REPORT.md   # Project documentation (to export as PDF)
│
└── lib/
    └── mysql-connector-java-8.0.19.jar

    ## 🖼️ Output Screenshot - Student Results

Below is a sample console output showing student data, marks, and calculated grades:

[<img width="927" height="897" alt="all-results" src="https://github.com/user-attachments/assets/1da12ef5-25b7-4f2e-91ac-b769350c0623" />](https://github.com/harishnaick/Student-Result-Management-System-Java-MySQL/blob/main/assets/screenshots/all-results.png)


