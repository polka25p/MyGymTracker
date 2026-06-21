# MyGymTracker 🏋️‍♀️💳

**MyGymTracker** is a custom web application designed for personal workout planning and fitness-related financial control. This project was created to replace chaotic text notes by combining a workout diary, gym membership tracking, and trainer payment logging into one single, convenient place.

The application is built following **clean architecture** principles without any high-level frameworks (such as Spring), which allows for a deep under-the-hood understanding of how Java EE / Jakarta EE components interact.

---

## 🚀 Key Features

### 1. Financial Monitor (Dashboard)
- **Membership Tracking:** Ability to manually set the next gym payment date via an intuitive calendar input (allowing for flexible 1–2 day adjustments). Automated alerts and notifications will appear when the payment deadline is approaching or overdue.
- **Trainer Payment Control:** Track individual personal training sessions ("Paid" / "In Debt"). The dashboard displays a clear summary of unpaid sessions with a quick "Mark as Paid" action button.

### 2. Workout Diary ("Today at the Gym")
- **Flexible Logging:** Since workout routines are adapted "on the fly" (often based on AI recommendations from Gemini), the app allows users to dynamically input exercises without being locked into strict, static templates.
- **Workout Entry Card:** Add dates, attach custom text notes (perfect for copying and pasting AI-generated plans), mark whether a trainer was present, and log executed exercises with specific weights, sets, and reps step-by-step.

---

## 🛠 Technical Stack

- **Language:** Java 21 (utilizing the modern Java Time API)
- **Web Server:** Apache Tomcat 10+ (Jakarta Servlet 6.0 Specification)
- **Database:** PostgreSQL
- **Data Access:** Pure JDBC (`PreparedStatement`, `Connection`, `ResultSet`)
- **Architecture Pattern:** MVC (Model-View-Controller) via Servlets and JSP
- **View Layer:** JSP (JavaServer Pages) + JSTL tags
- **Build System:** Maven
- **Testing:** JUnit 5 (Jupiter)

---

## 📂 Project Structure (MVC Layers)

The codebase is cleanly separated into logical layers within the `org.gym` package:
- `model/` — Plain Old Java Objects (POJO) representing domain entities (`Workout`, `Exercise`, `Membership`).
- `dao/` — Data Access Object layer containing raw SQL queries via JDBC to handle database operations.
- `service/` — Business logic layer handling date calculations and payment status evaluations.
- `controller/` — Java Servlets (`HttpServlet`) handling incoming HTTP requests and routing.
- `util/` — Utility classes, including the `ConnectionManager` to manage PostgreSQL database connectivity.
- `src/main/webapp/` — Front-end assets, including JSP pages and the deployment descriptor (`web.xml`).

---

## 🛠 Local Setup Instructions (In Development)

1. **Clone the Repository:**
   ```bash
   git clone [https://github.com/your-username/MyGymTracker.git](https://github.com/your-username/MyGymTracker.git)
