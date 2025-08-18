# To-Do List Application

A full-stack web application for managing personal to-do tasks with user authentication and secure session management.

## 🚀 Features

- **User Authentication**: Secure registration and login system
- **JWT-based Security**: Token-based authentication with HTTP-only cookies
- **Task Management**: Create, read, update, and delete to-do items
- **Secure Logout**: Proper session invalidation on both frontend and backend
- **Responsive UI**: Modern and intuitive user interface
- **Data Persistence**: MySQL database integration with Hibernate/JPA

## 🛠️ Technology Stack

### Backend

- **Java 21** - Programming language
- **Spring Boot 3.5.0** - Application framework
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data access layer
- **MySQL 8** - Database
- **JWT (JSON Web Tokens)** - Secure authentication
- **Maven** - Build tool

### Frontend

- **HTML5** - Markup
- **CSS3** - Styling
- **JavaScript (ES6+)** - Client-side logic
- **Fetch API** - HTTP requests

## 📋 Prerequisites

Before running this application, make sure you have:

- **Java 21** or higher installed
- **Maven 3.6+** installed
- **MySQL 8.0+** running locally
- **Git** for version control

## ⚙️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/Rancy21/todo_app.git
cd todo_app
```

### 2. Database Setup

1. Start your MySQL server
2. Create a database named `todo_app_db`:

```sql
CREATE DATABASE todo_app_db;
```

### 3. Configure Application Properties

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todo_app_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 4. Build and Run

```bash
# Build the application
mvn clean install

# Run the application
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## 🔐 Authentication & Security

### User Registration

- **Endpoint**: `POST /api/auth/register`
- **Features**: Email validation, password encryption, duplicate user prevention

### User Login

- **Endpoint**: `POST /api/auth/login`
- **Features**: JWT token generation, secure HTTP-only cookies, session management

### User Logout

- **Endpoint**: `POST /api/auth/logout`
- **Features**:
  - Server-side session invalidation
  - JWT cookie clearing
  - Security context cleanup
  - Graceful error handling on frontend

## 📝 API Endpoints

### Authentication

| Method | Endpoint             | Description         |
| ------ | -------------------- | ------------------- |
| POST   | `/api/auth/register` | Register a new user |
| POST   | `/api/auth/login`    | User login          |
| POST   | `/api/auth/logout`   | User logout         |

### Todo Management

| Method | Endpoint                | Description          |
| ------ | ----------------------- | -------------------- |
| GET    | `/api/todo/get`         | Get all user's todos |
| POST   | `/api/todo/create`      | Create a new todo    |
| PUT    | `/api/todo/update/{id}` | Update a todo        |
| DELETE | `/api/todo/delete/{id}` | Delete a todo        |

## 🎯 Usage

1. **Register**: Create a new account with email and password
2. **Login**: Access your personal dashboard
3. **Manage Tasks**:
   - Add new to-do items
   - Mark tasks as complete/incomplete
   - Edit task descriptions
   - Delete unwanted tasks
4. **Logout**: Securely end your session

## 🔧 Configuration

### JWT Settings

- **Secret Key**: Configured in `application.properties`
- **Expiration**: 1 hour (3600000 ms)
- **Cookie Security**: HTTP-only, Secure, SameSite=Lax

### Database Settings

- **Auto DDL**: `update` (creates/updates tables automatically)
- **SQL Logging**: Enabled for development
- **Dialect**: MySQL8Dialect

## 🏗️ Project Structure

```
src/
├── main/
│   ├── java/com/larr/todo/todo_app/
│   │   ├── controller/          # REST controllers
│   │   ├── model/              # Entity classes
│   │   ├── service/            # Business logic
│   │   ├── security/           # Security configuration
│   │   ├── dto/                # Data transfer objects
│   │   └── TodoAppApplication.java
│   └── resources/
│       ├── static/             # Frontend assets
│       │   ├── css/
│       │   ├── js/
│       │   └── index.html
│       └── application.properties
└── test/                       # Test files
```

## 🔒 Security Features

- **Password Encryption**: BCrypt hashing
- **JWT Authentication**: Secure token-based auth
- **HTTP-only Cookies**: Prevents XSS attacks
- **CORS Configuration**: Controlled cross-origin requests
- **Input Validation**: Server-side validation
- **Session Management**: Proper logout handling

## 🚀 Recent Updates

### Logout Functionality Enhancement

- **Backend Logout Endpoint**: Added `/api/auth/logout` for proper session termination
- **Frontend Integration**: Updated JavaScript to call backend logout
- **Security Improvements**:
  - Server-side JWT cookie clearing
  - Security context cleanup
  - Graceful error handling
  - Fallback mechanisms for network issues

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👨‍💻 Author

**Larry** - Full Stack Developer

## 🐛 Known Issues

- None currently reported

## 📞 Support

For support, email rancyontsandaga@gmail.com or create an issue in the repository.
