# 🚗 Vehicle Service Tracking System

A Hibernate + MySQL based console application to manage **customers, vehicles, and service records** for a vehicle service center.

---

## 📘 Project Overview

Service centers often track customer and vehicle details manually.  
This system automates the process by maintaining structured records of:
- Customers and their vehicles (one-to-many)
- Vehicle service history
- Service cost and description
- Simple CRUD operations (Create, Read, Update, Delete)

---

## 🧰 Tech Stack

| Component | Technology Used |
|------------|-----------------|
| **Language** | Java |
| **Framework** | Hibernate ORM |
| **Database** | MySQL |
| **Build Tool** | Maven |
| **IDE** | Eclipse |

---

## 🧩 Hibernate Concepts Used

- `@OneToMany` and `@ManyToOne` relationships  
- `@Entity`, `@Table`, `@Id`, `@GeneratedValue`  
- Hibernate **SessionFactory** setup  
- CRUD operations with **Session**  
- Automatic schema generation from annotations  
- Criteria Query API (optional for reports)

---

## ⚙️ Setup Instructions

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/KAVINKUMARV63/VehicleService-TrackingSystem-Hibernate.git
