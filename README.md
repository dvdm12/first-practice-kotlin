# Hospital Management System - Console Application

## Exercise

* Exercise: #2

## Class Diagram

![Class Diagram](assets/appDiagram.png)

## Description

This is a console-based Hospital Management System implemented in Kotlin. It allows managing doctors and patients with the following functionalities:

* Load doctors and patients from JSON files.
* List all doctors.
* Show the oldest doctor.
* Show doctors by specialty.
* Add new doctors and patients.
* Assign patients to doctors.
* Show the percentage of patients by gender.
* Show patients by doctor license.
* Deactivate doctors (at least one doctor must remain active).

The application is designed as a CLI (Command-Line Interface) for simplicity and portability.

## Requirements

* Gradle 9.0
* JDK 17

## Running the Application

1. Compile and build the project using Gradle:

```bash
./gradlew build
```

2. Run the JAR file:

```bash
java -jar app/build/libs/app.jar
```

The application will start in the terminal, providing an interactive menu for the user.

## Project Structure

* `App.kt` - Main application entry point.
* `controller/` - Contains the business logic for doctors and patients.
* `models/` - Data classes for Doctor, Patient, Person, City, and Street.
* `view/` - Handles user input and output in the console.
* `resources/` - JSON files for initial data (`doctors.json`, `patients.json`).
* `assets/appDiagram.png` - UML class diagram of the system.

## Authors

* **David Mantilla Aviles** – Code: 240220212015
* **Mariana Osorio Hernandez** – Code: 24020211026
* **Juan Caicedo** – Code: 240220232033


