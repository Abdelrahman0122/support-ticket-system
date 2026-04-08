support-ticket-system/
│
├── src/main/java/com/example/supportsystem/
│   ├── config/
│   │   ├── SecurityConfig.java
│   │   └── JwtAuthFilter.java
│   │
│   ├── controller/
│   │   ├── AuthController.java
│   │   └── TicketController.java
│   │
│   ├── dto/
│   │   ├── AuthRequest.java
│   │   ├── AuthResponse.java
│   │   ├── TicketRequest.java
│   │   └── TicketResponse.java
│   │
│   ├── model/
│   │   ├── User.java
│   │   ├── Ticket.java
│   │   └── enums/
│   │       ├── Role.java
│   │       ├── TicketStatus.java
│   │       └── Priority.java
│   │
│   ├── repository/
│   │   ├── UserRepository.java
│   │   └── TicketRepository.java
│   │
│   ├── service/
│   │   ├── AuthService.java
│   │   ├── TicketService.java
│   │   └── impl/
│   │       ├── AuthServiceImpl.java
│   │       └── TicketServiceImpl.java
│   │
│   ├── security/
│   │   ├── JwtService.java
│   │   └── CustomUserDetailsService.java
│   │
│   ├── exception/
│   │   └── GlobalExceptionHandler.java
│   │
│   └── SupportSystemApplication.java
│
├── src/main/resources/
│   └── application.yml
│
└── pom.xml