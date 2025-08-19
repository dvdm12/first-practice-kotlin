package org.example.hospital.controller

import models.Patient
import models.City
import models.Street
import java.io.File
import org.json.JSONArray
import org.json.JSONObject

class PatientController {

    private val patients = mutableListOf<Patient>()
    private val filePath = "src/main/resources/patients.json"
    

    fun loadPatientsFromJson() {
        val fileText = File(filePath).readText()
        val jsonArray = JSONArray(fileText)

        for (i in 0 until jsonArray.length()) {
            val patientObj = jsonArray.getJSONObject(i)
            val name = patientObj.getString("name")
            val cc = patientObj.getString("cc")
            val gender = patientObj.getString("gender")
            val email = patientObj.getString("email")
            val phone = patientObj.getString("phone")

            // Extract city object
            val cityObj = patientObj.getJSONObject("city")
            val cityName = cityObj.getString("name")
            val postalCode = cityObj.getString("postalCode")

            val streetsArray = cityObj.getJSONArray("streets")
            val streetsList = mutableListOf<Street>()
            for (j in 0 until streetsArray.length()) {
                val streetObj = streetsArray.getJSONObject(j)
                streetsList.add(Street(streetObj.getString("name")))
            }

            val city = City(cityName, postalCode, streetsList)
            val patient = Patient(name, cc, gender, email, phone, city)
            patients.add(patient)
        }
    }


    /** Create - add a new patient */
    fun addPatient(patient: Patient) {
        patients.add(patient)
    }

    /** Read - get all patients */
    fun getAllPatients(): List<Patient> {
        return patients
    }

    /** Read - get patient by CC */
    fun getPatientByCC(cc: String): Patient? {
        return patients.find { it.cc == cc }
    }

    /** Update - update patient by CC */
    fun updatePatient(cc: String, updatedPatient: Patient): Boolean {
        val index = patients.indexOfFirst { it.cc == cc }
        return if (index != -1) {
            patients[index] = updatedPatient
            true
        } else false
    }

    fun calculatePercentageByGender(): Double{
        return 0.0
    }

    /** Delete - remove patient by CC */
    fun deletePatient(cc: String): Boolean {
        val patient = patients.find { it.cc == cc }
        return if (patient != null) {
            patients.remove(patient)
            true
        } else false
    }

    /** Save current patients list back to JSON file */
    fun savePatientsToJson() {
        val jsonText = patients.joinToString(",\n", "[\n", "\n]") { patient ->
            """
            {
                "name": "${patient.name}",
                "cc": "${patient.cc}",
                "gender": "${patient.gender}",
                "email": "${patient.email}",
                "phone": "${patient.phone}",
                "cityName": "${patient.city.name}",
                "postalCode": "${patient.city.postalCode}"
            }
            """.trimIndent()
        }
        File(filePath).writeText(jsonText)
    }
}
