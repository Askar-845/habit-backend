Simple Habit Tracker – Backend

This is the Spring Boot backend for the Simple Habit Tracker application.
It manages users, their habits, and habit progress (milestones).

The system allows users to:

Create habits

Track habit duration

Monitor progress using milestones

🚀 Live Backend URL (Optional – if deployed)

👉 Backend URL:
https://your-backend-url/api/habits

⚠️ Important Note (Free Hosting):

Backend may take 30–60 seconds to wake up

First request may return empty response []

Please wait until backend is fully started

(If you have not deployed yet, you can remove this section)

📊 Project Presentation

👉 Download Project PPT
(Add your PPT Google Drive / local link)

🛠️ Tech Stack

Java

Spring Boot

Spring Data JPA

H2 Database

Maven

(Optional if used)

Docker

SonarCloud

🧩 Backend Features

User Management (CRUD)

Habit Management (CRUD)

Milestone Tracking

RESTful APIs

In-memory H2 database for simplicity

▶️ Run Project Locally
1️⃣ Clone the repository
git clone https://github.com/your-username/habit-tracker-backend.git

2️⃣ Go to project directory
cd habittracker

3️⃣ Run Spring Boot application
./mvnw spring-boot:run

🌐 Backend runs at
http://localhost:8080

🗄️ H2 Database Console
http://localhost:8080/h2-console


Login details:

JDBC URL : jdbc:h2:mem:habitdb
Username : sa
Password : (empty)

🧠 Project Description (For Viva)

“This project is a simple habit tracking system built using Spring Boot.
Users can create habits and track their progress using milestones.
H2 database is used for easy testing and demonstration.”

🔍 SonarCloud (Optional)

SonarCloud analysis integrated using GitHub Actions
Quality Gate passed successfully ✅

(Remove this section if you didn’t use SonarCloud)

🌐 Frontend (Optional)

Frontend can be built using React / Angular / HTML.

👉 Frontend Repository:
https://github.com/your-username/habit-tracker-frontend

👉 Live Frontend URL:
https://your-frontend-url.vercel.app
