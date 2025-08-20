package org.example.hospital.view

import org.example.hospital.controller.DoctorController
import org.example.hospital.controller.PatientController
import models.*
import java.util.Scanner
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HospitalView(
    private val doctorController: DoctorController,
    private val patientController: PatientController
) {

    private val scanner = Scanner(System.`in`)

    fun start() {
        println("Welcome to the Hospital Management System.")

        while (true) {
            println(
                """
                |Choose an option:
                |1. List all doctors
                |2. Show oldest doctor
                |3. Show doctors amount and salary by specialty
                |4. Add a new doctor
                |5. Add a new patient
                |6. Assign patient to a doctor
                |7. Show percentage of patients by gender
                |8. Show patients by doctor license
                |9. Desactivate Doctor
                |10. Show all doctors salary
                |0. Exit
                """.trimMargin()
            )

            val input = try { 
                val line = scanner.nextLine()
                if (line == null || line.isBlank()) {
                    println("No input detected. Exiting...")
                    break
                }
                line.trim()
            } catch (e: NoSuchElementException) {
                println("No input detected. Exiting...")
                break
            }

            when (input) {
                "1" -> listAllDoctors()
                "2" -> doctorController.showTheOldestDoctor()
                "3" -> showDoctorsBySpecialty()
                "4" -> addNewDoctor()
                "5" -> addNewPatient()
                "6" -> assignPatientToDoctor()
                "7" -> patientController.showPercentagePatientsByGender()
                "8" -> showPatientsByDoctorLicense()
                "9" -> deactivateDoctorMenu() 
                "10" -> doctorController.calculateAllSalary()
                "0" -> {
                    println("Exiting...")
                    return
                }
                else -> println("Invalid option, try again.")
            }
        }
    }

    private fun listAllDoctors() {
        val doctors = doctorController.getAllDoctors()
        if (doctors.isEmpty()) println("No doctors available.")
        else doctors.forEach {
            println("Name: ${it.name}, Specialty: ${it.specialty}, Salary: $${it.salary} pesos, Is Activated? ${it.isActivated} , First Year ${it.yearJoined.toString()}")
        }
    }

    private fun deactivateDoctorMenu() {
        val doctors = doctorController.getAllDoctors()
        if (doctors.isEmpty()) {
            println("No doctors available.")
            return
        }

        println("List of doctors:")
        doctors.forEach { 
            val status = if (it.isActivated) "Active" else "Inactive"
            println("Name: ${it.name}, License: ${it.licenseNumber}, Status: $status")
        }

        print("Enter the license number of the doctor to deactivate: ")
        val license = scanner.nextLine().trim()

        val success = doctorController.deactivateDoctorByLicense(license)
        if (success) println("Doctor with license '$license' has been deactivated.")
        else println("Could not deactivate the doctor. Ensure at least one doctor remains active or check the license.")
    }

    private fun showDoctorsBySpecialty() {
        val specialties = doctorController.getAllDoctors()
        .map { it.specialty }
        .distinct()
        .sorted()

        if (specialties.isEmpty()) {
            println("No doctors available to show specialties.")
            return
        }

        println("Available specialties:")
        specialties.forEachIndexed { index, specialty ->
            println("${index + 1}. $specialty")
        }

        print("Enter specialty (name or number): ")
        val input = scanner.nextLine().trim()

        val chosenSpecialty = input.toIntOrNull()?.let { index ->
            specialties.getOrNull(index - 1)
        } ?: specialties.find { it.equals(input, ignoreCase = true) }

        if (chosenSpecialty == null) {
            println("Invalid specialty.")
            return
        }

        val amount = doctorController.getDoctorsAmountBySpeciality(chosenSpecialty)
        val totalSalary = doctorController.calculateDoctorsSalaryBySpeciality(chosenSpecialty)
        println("There are $amount doctors in specialty '$chosenSpecialty' with total salary: $totalSalary")
    }


    private fun addNewDoctor() {
        println("Enter doctor details:")
        print("Name: "); val name = scanner.nextLine()
        print("CC: "); val cc = scanner.nextLine()
        print("Gender (M/F): "); val gender = scanner.nextLine().uppercase()
        print("Email: "); val email = scanner.nextLine()
        print("License Number: "); val license = scanner.nextLine().uppercase()
        print("Salary: "); val salary = scanner.nextLine().toIntOrNull() ?: 0
        print("Specialty: "); val specialty = scanner.nextLine()
        print("Year Joined (yyyy-MM-dd): "); val year = scanner.nextLine()
        val yearJoined = try { LocalDate.parse(year, DateTimeFormatter.ofPattern("yyyy-MM-dd")) } 
                         catch (e: Exception) { LocalDate.now() }

        val doctor = Doctor(name, cc, gender, email, license, salary, specialty, yearJoined, true)
        doctorController.addDoctor(doctor)
        println("Doctor added successfully.")
    }

    private fun addNewPatient() {
        println("Enter patient details:")
        print("Name: "); val name = scanner.nextLine()
        print("CC: "); val cc = scanner.nextLine()
        print("Gender (M/F): "); val gender = scanner.nextLine()
        print("Email: "); val email = scanner.nextLine()
        print("Phone: "); val phone = scanner.nextLine()

        println("\nChoose a city:")
        ColombianCity.values().forEachIndexed { index, city ->
            println("${index + 1}. ${city.displayName}")
        }
        print("Enter option (1-${ColombianCity.values().size}): ")
        val cityIndex = scanner.nextLine().toIntOrNull()?.minus(1) ?: 0
        val chosenCity = ColombianCity.fromInt(cityIndex) ?: ColombianCity.BOGOTA

        print("Postal code for ${chosenCity.displayName}: "); val postalCode = scanner.nextLine()

        val streets = mutableListOf<Street>()
        print("Number of streets in this city: ")
        val nStreets = scanner.nextLine().toIntOrNull() ?: 0
        for (i in 1..nStreets) {
            print("Street #$i name: "); streets.add(Street(scanner.nextLine()))
        }

        val city = City(chosenCity.displayName, postalCode, streets)
        val patient = Patient(name, cc, gender, email, phone, city)
        patientController.addPatient(patient)
        println("\nPatient '${patient.name}' added successfully in city '${city.name}'.")
    }

    private fun assignPatientToDoctor() {
        val patients = patientController.getAllPatients()
        if (patients.isEmpty()) { println("No patients available."); return }

        println("List of patients:")
        patients.forEach { println("Name: ${it.name}, CC: ${it.cc}") }

        print("Enter the CC of the patient to assign: ")
        val patientCC = scanner.nextLine()
        val patient = patientController.getPatientByCC(patientCC)
        if (patient == null) { println("Patient not found."); return }

        val doctors = doctorController.getAllDoctors()
        if (doctors.isEmpty()) { println("No doctors available."); return }

        println("List of doctors:")
        doctors.forEach { println("Name: ${it.name}, License: ${it.licenseNumber}, Specialty: ${it.specialty}") }

        print("Enter the license number of the doctor: ")
        val license = scanner.nextLine()
        val success = doctorController.addPatientToDoctor(license, patient)
        if (success) println("Patient '${patient.name}' assigned to doctor with license '$license'.")
        else println("Doctor not found.")
    }

    private fun showPatientsByDoctorLicense() {
        val doctors = doctorController.getAllDoctors()
        if (doctors.isEmpty()) {
            println("No doctors available.")
            return
        }

        println("List of doctors:")
        doctors.forEach { println("Name: ${it.name}, License: ${it.licenseNumber}, Specialty: ${it.specialty}") }

        print("Enter the license number of the doctor: ")
        val license = scanner.nextLine().trim()

        val patients = doctorController.getPatientsByLicense(license)

        if (patients.isEmpty()) {
            println("No patients found for doctor with license '$license'.")
            return
        }

        println("Patients assigned to doctor with license '$license':")
        patients.forEachIndexed { index, patient ->
            println("${index + 1}. Name: ${patient.name}, CC: ${patient.cc}, Gender: ${patient.gender}, Email: ${patient.email}, Phone: ${patient.phone}, City: ${patient.city.name}")
        }
    }

}
