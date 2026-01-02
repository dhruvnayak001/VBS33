# VBS - Virtual Banking System

A full-stack Spring Boot banking platform with secure transactions, role-based access, and complete audit logging & built using Spring Boot, MySQL.

## ✨ Key Features

🔐 **Secure Authentication** | 💰 **Full Transactions** | 📊 **Complete History** | 👥 **Admin Control** | ✅ **Validation**

- Role-based access (customer & admin)
- Deposits, withdrawals, peer-to-peer transfers
- Transaction passbook & system audit logs
- User management with sorting & search
- Overdraft prevention, self-transfer blocking

## 🏗️ Tech Stack

| Component | Technology |
|-----------|-----------|
| **Backend** | Spring Boot 3.5.9, Java 17 |
| **Database** | MySQL 8.0+, JPA/Hibernate |
| **Frontend** | HTML5, CSS, JavaScript |
| **Build** | Maven |
| **Port** | 8081 |


## 📂 Project Structure

```
demo/
├── src/main/java/com/vbs/demo/
│   ├── controller/       (UserController, TransactionController, HistoryController)
│   ├── models/           (User, Transaction, History)
│   ├── dto/              (LoginDto, TransactionDto, TransferDto, UpdateDto, DisplayDto)
│   └── repositories/     (UserRepo, TransactionRepo, HistoryRepo)
├── src/main/resources/
│   ├── application.properties
│   └── static/           (HTML pages: login, signup, dashboard, admin, history, etc.)
├── pom.xml
└── README.md
```

## 🔒 Security

✅ Role-based access control  
✅ Overdraft prevention  
✅ Self-transfer blocking  
✅ Auto-audit logging  
✅ CORS enabled  
✅ Unique constraints (username, email)  

## 🎯 Workflows

**Customer**: Register → Login → Dashboard → Deposit/Withdraw/Transfer → Passbook  
**Admin**: Login (admin role) → Admin Panel → Manage Users → View History

