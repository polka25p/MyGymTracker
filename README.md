# MyGymTracker 🏋️‍♀️💳

**MyGymTracker** is a custom web application designed for personal workout planning and fitness-related financial control. This project replaces chaotic text notes by combining a highly flexible workout diary, gym membership tracking, and personal trainer payment logging into a single, cohesive dashboard.

The application is built following **Clean Architecture (MVC)** principles using pure Java web components without any high-level frameworks (such as Spring). This ensures a deep, under-the-hood mastery of native Java EE / Jakarta EE lifecycle interactions.

---

## 🚀 Key Features (As Seen in UI)

### 1. Financial Monitor & Membership Control

* **Membership Tracking:** Displays days remaining until your next gym payment (`Membership due in X days`) with clear semantic color coding based on urgency.
* **Active Debt Tracker:** Displays your outstanding balance for sessions conducted with a coach (`Trainer Debt: X UAH`). Includes an instant **"Mark as Paid"** action button that triggers a secure database update to reset your debt balance dynamically.

### 2. Workout Diary & Dynamic Exercise Logging

* **Log New Workout Form:** An integrated lateral panel allows users to quickly add a session date, choose whether a trainer was present (`With Trainer` checkbox), and attach extensive training notes.
* **Dynamic Training Cards & Modals:** Displays logged sessions chronologically in a dark-graphite grid layout. Users can click **"View Details"** to view exact exercises, weights, and set/rep configurations inside a custom modal.
* **PRG Pattern Workflow:** Employs the **Post/Redirect/Get** pattern inside controllers to eliminate accidental duplicate database entries upon page refreshes (F5).

---

## 🛠 Technical Stack

* **Language:** Java 21 (utilizing modern Java Time API)
* **Web Server:** Apache Tomcat 10.1+ (Jakarta Servlet 6.0 Specification)
* **Database:** PostgreSQL
* **Data Access:** Pure JDBC (`PreparedStatement`, `Connection`, `ResultSet` with strict resource management)
* **Architecture Pattern:** MVC (Model-View-Controller) via Servlets and JSP
* **View Layer:** JSP (JavaServer Pages) + Jakarta JSTL core tags
* **Build System:** Maven
* **Testing:** JUnit 5 (Jupiter)

---

## 📋 Prerequisites

Ensure the following tools are available locally before starting deployment:

| Tool | Recommended Version |
| --- | --- |
| JDK | 21 |
| Maven | 3.9+ |
| PostgreSQL | 15+ |
| Apache Tomcat | 10.1.x |

Make sure `java`, `mvn`, and `psql` are registered in your environment path variables (or use components integrated with IntelliJ IDEA).

---

## 🛠 Local Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/MyGymTracker.git
cd MyGymTracker

```

### 2. Configure the Database

#### Create Database Instance

Open your terminal or database CLI client (pgAdmin / DBeaver) and create the system catalog:

```bash
psql -U postgres -c "CREATE DATABASE gym_tracker;"

```

#### Apply Schema Scripts

Execute the consolidated schema setup to configure the structural foreign relationships (`gym_membership`, `workouts`, `exercises` tables):

```bash
psql -U postgres -d gym_tracker -f src/main/resources/db/init.sql

```

#### Update Connection Credentials

Open `src/main/resources/application.properties` and replace user configurations with your active local environment values:

```properties
db.url=jdbc:postgresql://localhost:5432/gym_tracker
db.username=postgres
db.password=YOUR_LOCAL_DB_PASSWORD

```

### 3. Build the Project

Compile classes, validate scopes, and package the primary Web Application Archive (`.war`) bundle via Maven:

```bash
mvn clean package

```

The deployable asset is generated at: `target/MyGymTracker.war`

### 4. Running Infrastructure Integration (Tomcat Configuration)

#### Standard IDE Deployment (IntelliJ IDEA Ultimate)

1. Navigate to the top execution dropdown and select **Edit Configurations...**
2. Click **`+`**, choose **Tomcat Server** -> **Local**.
3. Link the path configuration to your Apache Tomcat 10.1+ server home folder.
4. Move to the **Deployment** tab, click **`+`** -> **Artifact**, and attach `MyGymTracker:war exploded`.
5. Set the **Application Context** field below to exactly `/` (root access context).
6. Click **Apply** and click the green **Run** triangle icon.

---

## 🌐 Application Navigation Paths

When deployed using a clean root context (`/`), access these resource mapping endpoints via your browser:

| Feature Route | URL Target | Context Execution Type |
| --- | --- | --- |
| Main Dashboard & Records | `http://localhost:8080/workouts` | **Servlet Layer Controller** (Loads DB entities before rendering) |

> ⚠️ **Important:** Do not try to bypass routers by opening internal view frames directly. Use `/workouts` to trigger database queries.

---

## 📂 Project Structure

```micro
MyGymTracker/
│
├── src/
│   ├── main/
│   │   ├── java/org/gym/
│   │   │   ├── model/       # Domain objects (POJO entities)
│   │   │   ├── dao/         # Data Access Object abstractions & JDBC implementations
│   │   │   ├── service/     # Business logic calculations (dates, debt evaluation metrics)
│   │   │   ├── controller/  # HTTP request orchestration endpoints (Servlets)
│   │   │   └── util/        # Infrastructure management (ConnectionManager parsing properties)
│   │   │
│   │   ├── resources/
│   │   │   ├── db/          # Relational structure initializers (init.sql)
│   │   │   └── application.properties # Main application properties file
│   │   │
│   │   └── webapp/
│   │       ├── WEB-INF/     # App configurations (web.xml)
│   │       └── workouts.jsp # Structured main dashboard views
│   │
│   └── test/java/org/gym/   # JUnit testing environment suites
│
├── pom.xml                  # Maven Dependency tree matrix
└── README.md

```

---

## 🔍 Troubleshooting

| Encountered Issue | Probable Structural Cause | Target Corrective Action |
| --- | --- | --- |
| `ClassNotFoundException: org.postgresql.Driver` | PostgreSQL driver jar missing from runtime engine context. | Verify dependency declaration scope in `pom.xml`. Ensure Maven successfully targets and bundles libraries into your built artifact. |
| Empty lists or raw expressions visible on pages. | Browsing raw JSP paths natively without going through controller filters. | Make sure you are navigating directly to `http://localhost:8080/workouts` so the servlet handles database population. |
| `java.net.ConnectException: Connection refused` | PostgreSQL instances offline or connection credentials mismatched. | Double check your active local cluster status on port `5432` and re-verify key-value tokens in `application.properties`. |
| Form resubmission creates identical duplicates on page refresh. | Missing post-processing redirect patterns in server routing logic. | Verify implementation blocks are safely processing state transitions via `response.sendRedirect()` (PRG flow pattern). |
