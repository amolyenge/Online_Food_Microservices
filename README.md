Spring Boot Microservices Project :-


A scalable food delivery system designed using Spring Boot microservices architecture, enabling seamless integration and performance.


 Key Components :- 
Backend Services

    1) API Gateway (Port: 8080) : Single entry point for requests, handling routing and load balancing.
    2) Eureka Service Discovery (Port: 8761) : Registers and discovers microservices for load balancing and monitoring.
    3) User Service (Port: 8081) : Manages user authentication, profiles, and JWT security.
    4) Restaurant Service (Port: 8082) : Handles restaurant data, menus, and image uploads.
    5) Order Service (Port: 8083) : Processes orders, integrates with users/restaurants, and tracks order statuses.
    6)Notification Service (Port: 8084) : Sends event-driven email notifications using Kafka.

Tech Stack :->

         1) Java 
         2) Spring Boot
         3) Microservices
         4) Kafka
         5) Spring Feign cloud
         6) JPA
         7) MYSQL
         8) Lombok
         9) JWT and Oauth
         10) Netflix Eureka
         11) SMTP Mail
         13) AOP
         14) Resillience4j
         15) SLF4J

