CAR RENTAL MANAGEMENT SYSTEM (V-RMS)
Setup & Run Guide

1. PREREQUISITES
-----------------------------------------------------------------------------
Ensure you have the following installed on your machine:
   - Java Development Kit (JDK) 17 or higher (You are using JDK 25)
   - PostgreSQL Database
   - Maven (or use the wrapper included in the project)
   - IntelliJ IDEA (Recommended) or Eclipse

2. DATABASE SETUP
-----------------------------------------------------------------------------
1. Open pgAdmin or your PostgreSQL command line.
2. Create a new database named 'car_rental_db':
   
   CREATE DATABASE car_rental_db;

3. The tables (vehicles, rentals, etc.) will be automatically created by 
   Hibernate when you run the application for the first time.

3. CONFIGURATION
-----------------------------------------------------------------------------
1. Open the file: src/main/resources/application.properties
2. Check your database username and password. Update them if they are different:

   spring.datasource.url=jdbc:postgresql://localhost:5432/car_rental_db
   spring.datasource.username=postgres
   spring.datasource.password=your_password_here

4. IMPORTANT: IMAGE UPLOADS FOLDER
-----------------------------------------------------------------------------
The system stores vehicle and license images locally.
1. Go to your project root folder (where this file is located).
2. Create a new folder named 'uploads' if it does not exist.
   
   Project_Folder/
   ├── src/
   ├── target/
   ├── uploads/  <-- Create this!
   ├── pom.xml
   └── ...

5. HOW TO RUN
-----------------------------------------------------------------------------
Option A: Using IntelliJ IDEA (Easiest)
   1. Open the project in IntelliJ.
   2. Locate 'CarRentalApplication.java' in src/main/java/com/example/car_rental/.
   3. Right-click the file and select "Run 'CarRentalApplication'".

Option B: Using Command Line (Terminal)
   1. Open terminal in the project root folder.
   2. Run the command:
      mvn spring-boot:run

6. ACCESSING THE APPLICATION
-----------------------------------------------------------------------------
Once the application started (look for "Started CarRentalApplication..." in logs):

   🔹 Customer Portal (Book a Car):
      http://localhost:8080/

   🔹 Admin Dashboard (Manage Fleet & Approvals):
      http://localhost:8080/login

      *Default Credentials:*
      Username: admin
      Password: 1234
      
7. TROUBLESHOOTING
-----------------------------------------------------------------------------
- If you see "Port 8080 is already in use":
  Stop any other running instances of the app or change server.port in application.properties.

- If images don't load:
  Ensure the 'uploads' folder exists in the project root.

=============================================================================
