## 🚀 Getting Started with Docker

This guide will help you run the application with Docker, including both the Spring Boot app and MySQL database.

### 📁 Prerequisites

* [Docker](https://www.docker.com/products/docker-desktop) installed
* [Docker Compose](https://docs.docker.com/compose/) (comes with Docker Desktop)

---

### ⚙️ Step 1: Build the Application

Make sure the Spring Boot application is compiled:

```bash
./mvnw clean package
```

> This will create a `target/app.jar` file.

---

### 📂 Step 2: Project Structure

```
.
├── Dockerfile
├── docker-compose.yml
├── src/
├── target/
│   └── app.jar
├── README.md
└── ...
```

---

### 🐳 Step 3: Start with Docker Compose

Run the application and MySQL together:

```bash
docker-compose up --build
```

This will:

* Build the Spring Boot application Docker image
* Start MySQL on port `3306`
* Start your app on port `8000`

---

### 🌐 Access

* Swagger UI: [http://localhost:8000/swagger-ui.html](http://localhost:8000/swagger-ui.html)
* API Base URL: `http://localhost:8000/api/...`

---

### 🛑 Stop the App

```bash
docker-compose down
```

