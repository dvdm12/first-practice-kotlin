package org.example.hospital.controller

import models.Doctor
import models.Patient
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DoctorController {

    private val doctors = mutableListOf<Doctor>()
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun initFromJson() {
        val path = "src/main/resources/doctors.json"
        val file = File(path)
        if (!file.exists()) {
            println("file doctors.json did not found out")
            return
        }

        val jsonString = file.readText()
        
        val entries = jsonString.trim().removePrefix("[").removeSuffix("]").split("},").map { it.trim() + "}" }

        for (entry in entries) {
            val name = Regex("\"name\"\\s*:\\s*\"([^\"]+)\"").find(entry)?.groupValues?.get(1) ?: ""
            val cc = Regex("\"cc\"\\s*:\\s*\"([^\"]+)\"").find(entry)?.groupValues?.get(1) ?: ""
            val gender = Regex("\"gender\"\\s*:\\s*\"([^\"]+)\"").find(entry)?.groupValues?.get(1) ?: ""
            val email = Regex("\"email\"\\s*:\\s*\"([^\"]+)\"").find(entry)?.groupValues?.get(1) ?: ""
            val licenseNumber = Regex("\"licenseNumber\"\\s*:\\s*\"([^\"]+)\"").find(entry)?.groupValues?.get(1) ?: ""
            val salary = Regex("\"salary\"\\s*:\\s*(\\d+)").find(entry)?.groupValues?.get(1)?.toInt() ?: 0
            val specialty = Regex("\"specialty\"\\s*:\\s*\"([^\"]+)\"").find(entry)?.groupValues?.get(1) ?: ""
            val yearJoinedStr = Regex("\"yearJoined\"\\s*:\\s*\"([^\"]+)\"").find(entry)?.groupValues?.get(1) ?: "2000-01-01"
            val isActivated = Regex("\"isActivated\"\\s*:\\s*(true|false)").find(entry)?.groupValues?.get(1)?.toBoolean() ?: true

            val doctor = Doctor(
                name = name,
                cc = cc,
                gender = gender,
                email = email,
                licenseNumber = licenseNumber,
                salary = salary,
                specialty = specialty,
                yearJoined = LocalDate.parse(yearJoinedStr, formatter),
                isActivated = isActivated
            )

            doctors.add(doctor)
        }

        println("it's been uploaded ${doctors.size} doctors from JSON")
    }
    
    // CRUD
    fun addDoctor(doctor: Doctor) {
        doctors.add(doctor)
    }

    fun removeDoctor(licenseNumber: String) {
        doctors.removeIf { it.licenseNumber == licenseNumber }
    }

    fun updateDoctor(licenseNumber: String, updatedDoctor: Doctor) {
        val index = doctors.indexOfFirst { it.licenseNumber == licenseNumber }
        if (index != -1) {
            doctors[index] = updatedDoctor
        }
    }

    fun getDoctorByLicense(licenseNumber: String): Doctor? {
        return doctors.find { it.licenseNumber == licenseNumber }
    }

    fun getAllDoctors(): List<Doctor> = doctors.toList()


    fun addPatientToDoctor(licenseNumber: String, patient: Patient): Boolean {
        val doctor = doctors.find { it.licenseNumber == licenseNumber }
        return if (doctor != null) {
            doctor.patients.add(patient)
            true
        } else {
            false
        }
    }
}
