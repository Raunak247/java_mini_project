# 💰 Smart Expense Manager (Java + Swing + MySQL)

A feature-rich **desktop-based expense management system** built using **Java Swing**, **File Handling**, and **MySQL (JDBC)**.
This project demonstrates core **OOP concepts**, **data structures**, and **real-world application design**.

---

## 🚀 Features

### 📊 Expense Management

* Add new expenses with date, category, amount, and description
* Edit existing records directly from the table
* Delete selected expenses

### 🔍 Search & Filtering

* Multi-field search (date, category, description)
* Instant filtering using keyword search

### 📈 Sorting

* Sort expenses by:

  * Amount
  * Date

### 💾 File Handling

* Save data to local file (`data.txt`)
* Load data from file
* Import external files (CSV format supported)

### 🗄️ Database Integration (MySQL)

* Save all expenses to database
* Load expenses from database
* Uses JDBC for database connectivity

### 🧮 Additional Utilities

* Calculate total expense
* Table auto-fill on row selection

---

## 🛠️ Tech Stack

| Layer        | Technology  |
| ------------ | ----------- |
| Language     | Java        |
| UI Framework | Swing       |
| Database     | MySQL       |
| Connectivity | JDBC        |
| Storage      | File System |

---

## 📂 Project Structure

```
📁 SmartExpenseManager
 ├── ExpenseUI.java          # UI Layer (Swing)
 ├── ExpenseManager.java    # Business Logic + DB + File Handling
 ├── data.txt               # Local storage file
```

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/your-username/smart-expense-manager.git
cd smart-expense-manager
```

---

### 2️⃣ Setup MySQL Database

Create database:

```sql
CREATE DATABASE testdb;
```

Create table:

```sql
CREATE TABLE expenses (
    date VARCHAR(20),
    category VARCHAR(20),
    amount DOUBLE,
    description VARCHAR(100)
);
```

---

### 3️⃣ Configure Database Credentials

Update inside `ExpenseManager.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/testdb";
private static final String DB_USER = "root";
private static final String DB_PASS = "root";
```

---

### 4️⃣ Run the Project

Compile and run:

```bash
javac ExpenseUI.java
java ExpenseUI
```

---

## 🧠 Concepts Used

* ✅ Object-Oriented Programming (Class, Object, Inheritance, Polymorphism)
* ✅ Data Structures (ArrayList)
* ✅ Searching (Linear Search)
* ✅ Sorting (Comparator / Bubble Sort concept)
* ✅ File Handling (BufferedReader / Writer)
* ✅ Database Connectivity (JDBC)
* ✅ Event-Driven Programming (Swing)

---

## 📌 Example Data Format

### File Format (`data.txt`)

```
2026-03-30|Food|250|Lunch
2026-03-30|Travel|100|Bus
```

### Import CSV Format

```
2026-03-30,Food,250,Lunch
2026-03-30,Travel,100,Bus
```

---

## 🎯 Use Cases

* Personal expense tracking
* Academic mini-project (Java + DBMS)
* Demonstration of full-stack Java fundamentals

---

## 🚀 Future Enhancements

* 📊 Graphs & Analytics Dashboard
* 🔐 User Authentication System
* 📁 Excel File Support
* 🌐 REST API (Spring Boot)
* ⚛️ React Frontend Integration

---

## 👨‍💻 Author

**Your Name**
Raunak Pantawane
Rachit deogirikar
* GitHub: https://github.com/Raunak247

---

## ⭐ Contribution

Contributions, issues, and feature requests are welcome!
Feel free to fork and improve the project.

---

## 📄 License

This project is open-source and available under the MIT License.

---
