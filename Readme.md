# Support Ticket System

A comprehensive support ticket management system built with Spring Boot, featuring JWT authentication, role-based access control, and complete ticket lifecycle management.

## ✨ Features

### 🔐 Authentication & Security
- JWT-based authentication with secure token management
- Role-based access control (USER, AGENT, ADMIN)
- Password encryption with BCrypt
- Method-level security authorization

### 👥 User Management
- User registration and login
- Admin user creation and management
- Role assignment (User, Agent, Administrator)
- Secure user profile management

### 🎫 Ticket Management
- Create, read, update, and delete support tickets
- Ticket status tracking (OPEN, IN_PROGRESS, RESOLVED, CLOSED)
- Priority levels (LOW, MEDIUM, HIGH)
- Ticket assignment to agents
- Role-based ticket access permissions

### 🛠 Technical Features
- RESTful API design
- PostgreSQL database integration
- JPA/Hibernate ORM
- Comprehensive error handling
- Input validation and data integrity

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL 12+

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd support-ticket-system
   ```

2. **Database Setup**
   ```sql
   CREATE DATABASE support_system;
   ```

3. **Configure Database**
   Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/support_system
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

The application will start on `http://localhost:8080`

## 📚 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - User login

### User Management (Admin Only)
- `GET /api/admin/users` - List all users
- `GET /api/admin/users/{id}` - Get user by ID
- `POST /api/admin/users` - Create new user with role

### Ticket Management
- `GET /api/tickets` - Get accessible tickets
- `GET /api/tickets/my` - Get user's own tickets
- `GET /api/tickets/assigned` - Get assigned tickets (Agent/Admin)
- `GET /api/tickets/{id}` - Get ticket by ID
- `POST /api/tickets` - Create new ticket
- `PUT /api/tickets/{id}` - Update ticket
- `PATCH /api/tickets/{id}/status` - Update ticket status
- `DELETE /api/tickets/{id}` - Delete ticket

## 🛠 Tech Stack

- **Backend**: Java 17, Spring Boot 3.3.5
- **Security**: Spring Security, JWT
- **Database**: PostgreSQL
- **ORM**: JPA/Hibernate
- **Build Tool**: Maven
- **Architecture**: Layered architecture (Controller → Service → Repository)

## 🏗 Project Structure

```
support-ticket-system/
├── src/main/java/com/example/support_ticket_system/
│   ├── config/           # Security & configuration
│   ├── controller/       # REST API endpoints
│   ├── domain/           # Entities & DTOs
│   ├── mapper/           # Entity-DTO mapping
│   ├── repository/       # Data access layer
│   ├── security/         # JWT & authentication
│   └── service/          # Business logic
├── src/main/resources/
│   └── application.properties
└── pom.xml
```

## 🔧 Configuration

### JWT Settings
```properties
jwt.secret=your-256-bit-secret-key
jwt.expiration=86400000
```

### Database Settings
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.datasource.url=jdbc:postgresql://localhost:5432/support_system
spring.datasource.username=postgres
spring.datasource.password=your_password
```

## 🧪 Testing the API

### 1. Register a User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"John Doe","email":"john@example.com","password":"password123"}'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"john@example.com","password":"password123"}'
```

### 3. Create a Ticket
```bash
curl -X POST http://localhost:8080/api/tickets \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"title":"Login Issue","description":"Cannot login","priority":"HIGH"}'
```

## 📋 User Roles & Permissions

| Role  | Description | Permissions |
|-------|-------------|-------------|
| USER  | Regular user | Create tickets, view own tickets |
| AGENT | Support agent | Manage assigned tickets, update status |
| ADMIN | Administrator | Full system access, user management |

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License.