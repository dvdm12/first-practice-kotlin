package org.example.hospital.controller

import models.Doctor
import models.Patient
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import org.json.JSONArray
import org.json.JSONObject

class DoctorController {

    private val doctors = mutableListOf<Doctor>()
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun initFromJson() {
    
        val inputStream = this::class.java.getResourceAsStream("/doctors.json")
            ?: run {
                println("file doctors.json not found in resources")
                return
            }

        val jsonString = inputStream.bufferedReader().use { it.readText() }

        val jsonArray = JSONArray(jsonString) 

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)

            val doctor = Doctor(
                name = jsonObject.optString("name", ""),
                cc = jsonObject.optString("cc", ""),
                gender = jsonObject.optString("gender", ""),
                email = jsonObject.optString("email", ""),
                licenseNumber = jsonObject.optString("licenseNumber", ""),
                salary = jsonObject.optInt("salary", 0),
                specialty = jsonObject.optString("specialty", ""),
                yearJoined = LocalDate.parse(
                jsonObject.optString("yearJoined", "2000-01-01"), 
                formatter
            ),
                isActivated = jsonObject.optBoolean("isActivated", true)
            )   

            doctors.add(doctor)
        }

        println("Uploaded ${doctors.size} doctors from JSON")
    }


    fun getDoctorsAmountBySpeciality(specialty:String): Int {
        return doctors
        .filter { it.specialty.equals(specialty) }
        .toList().size
    }

    fun calculateDoctorsSalaryBySpeciality(specialty:String): Int{
        return doctors
        .filter{it.specialty.equals(specialty)}
        .sumOf { it.salary }
        .toInt()
    }

    fun calculateAllSalary():Int{
        return doctors.sumOf { it.salary }
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

    fun showTheOldestDoctor(){
        var doctor:Doctor? = doctors.minByOrNull { it.yearJoined }
        
        doctor?.let {
            val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") 
            val joinedString = it.yearJoined.format(formatter)
            println("The oldest doctor is: ${it.name}, they were introduced since: $joinedString")
        } ?: run {
            println("No doctors available")
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
