# 📱 Fetal Health Analysis — Android App

### Native Android companion app for the Fetal Health Analysis deep learning system

[![Java](https://img.shields.io/badge/Java-Android-007396?style=flat-square&logo=java&logoColor=white)](https://www.java.com)
[![Android](https://img.shields.io/badge/Android-Studio-3DDC84?style=flat-square&logo=android&logoColor=white)](https://developer.android.com/studio)
[![SQL Server](https://img.shields.io/badge/Database-SQL_Server-CC2927?style=flat-square&logo=microsoftsqlserver&logoColor=white)](https://www.microsoft.com/sql-server)

> A native Android application providing mobile access to the Fetal Health Analysis system — allowing users to log in, select an ultrasound machine type, and view automated fetal biometric measurements (AC, BPD, HC) on the go.

**Related project:** [fetal-health-analysis](https://github.com/sheikhalyan/fetal-health-analysis) — the core Flask + TensorFlow Lite deep learning system this app connects to

---

## What It Does

- **User authentication** — registration and login with session management
- **Machine-specific dashboards** — separate flows for Aloka, Voluson E6, Voluson S10, and Voluson S8 ultrasound machines, matching the four machine types supported by the underlying deep learning models
- **Biometric measurement views** — dedicated screens for Abdominal Circumference (AC) and Biparietal Diameter (BPD) results
- **Direct database connectivity** — communicates with a SQL Server backend via JDBC (JTDS driver)

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| **Platform** | Android (Java) |
| **Build System** | Gradle |
| **Database** | SQL Server |
| **DB Connectivity** | JTDS JDBC Driver (`jtds-1.3.1.jar`) |
| **UI** | Android XML layouts, custom drawables, Montserrat font |

---

## Project Structure

```
FinalFyp/
│
├── app/
│   ├── libs/
│   │   └── jtds-1.3.1.jar                  # SQL Server JDBC driver
│   │
│   └── src/main/java/com/example/finalfyp/
│       ├── LoginActivity.java               # User login
│       ├── RegisterActivity.java            # User registration
│       ├── Dashboard.java                   # Main dashboard
│       ├── SessionManager.java              # Session/auth state management
│       ├── MainActivity.java
│       ├── Checking.java
│       ├── MyAPI.java
│       │
│       ├── AbdominalCircumference.java      # AC measurement screen
│       ├── BPDIameter.java                  # BPD measurement screen
│       │
│       ├── Aloka.java                       # Aloka machine flow
│       ├── VolusionE6.java                  # Voluson E6 machine flow
│       ├── VolusionS10.java                 # Voluson S10 machine flow
│       ├── VolusionS8.java                  # Voluson S8 machine flow
│       │
│       └── Connection/
│           └── ConnectionClass.java         # SQL Server JDBC connection handler
│
├── DB Script/
│   └── Scripts.sql                          # Database schema (sanitized)
│
├── build.gradle
├── settings.gradle
└── gradle.properties
```

---

## Getting Started

### Prerequisites
- Android Studio (latest stable)
- SQL Server instance (local or network-accessible)
- JDK 8+

### Setup

1. Clone the repository
   ```bash
   git clone https://github.com/sheikhalyan/FinalFyp.git
   ```
2. Open the project in Android Studio
3. Run `DB Script/Scripts.sql` against your SQL Server instance to set up the schema
4. Update `app/src/main/java/com/example/finalfyp/Connection/ConnectionClass.java` with your own database server IP, username, and password:
   ```java
   private static final String SERVER = "YOUR_SERVER_IP";
   private static final String USER = "YOUR_DB_USER";
   private static final String PASSWORD = "YOUR_DB_PASSWORD";
   ```
5. Sync Gradle and run on an emulator or physical device

---

## ⚠️ Known Limitations

This was built as an academic Final Year Project and has some architectural choices that would need to change for production use:

- **Direct JDBC connection from the mobile client** — the app connects directly to SQL Server over the network rather than through a REST API layer. This was a deliberate simplification for the academic scope of the project, but in production, the database should sit behind a proper API layer (e.g. the Flask backend in the companion repo), never exposed directly to client devices.
- **Credentials are configured in source** rather than pulled from a secure config or environment-based system — acceptable for an academic demo, but a clear area for improvement if this were extended into a real product.

These limitations are intentionally documented here as an example of recognizing trade-offs made for a university project timeline versus production-grade architecture.

---

## Disclaimer

This application was developed for **academic and research purposes** as part of a Final Year Project. It is not a certified medical tool and must not be used as a substitute for professional clinical judgment.

---

## Context

Mobile companion app for the **Fetal Health Analysis System**, a Final Year Project at PAF-KIET applying deep learning (V-Net models) to automate fetal biometric measurement from ultrasound images.

---

## Author

**Sheikh Alyan** — BS Computer Science, PAF-KIET (2020–2024)

[![GitHub](https://img.shields.io/badge/GitHub-@sheikhalyan-181717?style=flat-square&logo=github)](https://github.com/sheikhalyan)
