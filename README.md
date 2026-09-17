# AnyBook Backend

Backend REST API for **AnyBook**, a full-stack Android appointment booking application that connects clients with service-based businesses such as salons, clinics, academies, and more.

The backend is built with **Java and Spring Boot** and provides APIs for authentication, user management, services, and appointment booking.

## 🚀 Features

* Client and Owner registration
* Client and Owner login
* JWT-based authentication
* Role-based access for Clients and Owners
* Business profile management
* Service management
* Appointment booking
* Appointment status management
* Booking history
* RESTful API architecture
* MongoDB database integration

## 🛠️ Technology Stack

* **Java 17**
* **Spring Boot**
* **Spring Security**
* **JWT**
* **MongoDB**
* **Maven**
* **REST APIs**
* **Render** for deployment

## 📂 Project Structure

```text
src/
├── main/
│   ├── java/
│   │   └── com.anybook.backend/
│   │       ├── controller/
│   │       ├── service/
│   │       ├── repository/
│   │       ├── entity/
│   │       ├── dto/
│   │       ├── security/
│   │       └── util/
│   │
│   └── resources/
│       └── application.properties
```

## 🔐 Authentication

AnyBook uses **JWT (JSON Web Token)** authentication with Spring Security.

Users can register and log in as either:

* **CLIENT**
* **OWNER**

After successful authentication, the server provides a JWT token that is used to authorize protected API requests.

## 🔗 API Modules

The backend provides REST APIs for:

### Authentication

* Client signup
* Owner signup
* Login

### Users

* User profile
* Profile updates
* Business information

### Services

* Create services
* Retrieve services
* Update service information
* Delete services

### Appointments

* Create appointments
* Retrieve bookings
* Update appointment status
* Manage booking information

## 🗄️ Database

The application uses **MongoDB** for storing:

* User information
* Business information
* Services
* Appointments

## 🌐 Deployment

The backend is deployed using **Render** and is connected to a MongoDB Atlas database.

## ▶️ Run Locally

### 1. Clone the repository

```bash
git clone https://github.com/Manan13062005/AnyBook-Backend.git
```

### 2. Open the project

Open the project in **IntelliJ IDEA** or any Java IDE with Maven support.

### 3. Configure environment variables

Configure the required MongoDB and JWT environment variables in your local environment.

### 4. Run the application

Run the Spring Boot application from the main application class.

The API will then be available on the configured local server.

## 📱 Android Frontend

The Android frontend of AnyBook is developed using **Kotlin and Jetpack Compose**.

Frontend Repository:
https://github.com/Manan13062005/AnyBook

## 👨‍💻 Developer

**Manan Kumar**

B.Tech CSE — MSIT Delhi

GitHub:
https://github.com/Manan13062005

---

⭐ If you find this project interesting, feel free to explore the repository.
