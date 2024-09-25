Courier Company Management System
This project is a Courier Company Management System built using Java Spring for the backend and Swing for the frontend.
The system is designed to manage the operations of a courier company, including package delivery management, customer information, tracking, and payment processing.

Features
Package Management: Create, update, and delete package delivery records.
Customer Management: Store and manage customer information.
Real-time Package Tracking: Track the status of deliveries in real-time.
Payment Processing: Handle payments for courier services.
Delivery Route Management: Manage and optimize delivery routes.
Admin Dashboard: View and manage all operations through an intuitive interface.
Technologies Used
Backend:
Java Spring Boot: Used for developing the backend REST API.
Spring Data JPA: For database operations.
MySQL: The database used for storing package, customer, and delivery information.
Spring Security: Secures endpoints with role-based access control (admin, employee, etc.).
Frontend:
Swing: Provides the GUI for interacting with the backend, allowing employees and admins to manage deliveries and customers.

Prerequisites
Before you begin, ensure you have the following installed:
Java 11+
Maven 3.6+
MySQL (or any preferred RDBMS)

Clone the Repository
git clone https://github.com/janetheboss/CourierCompany.git
cd CourierCompany

Build the Backend
Use Maven to install dependencies and build the project:
mvn clean install

Run the Backend
Once the backend is built, you can run the Spring Boot application:
mvn spring-boot:run

