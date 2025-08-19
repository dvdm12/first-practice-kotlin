package org.example.hospital

import org.example.hospital.controller.DoctorController
import org.example.hospital.controller.PatientController
import models.Patient
    
fun main(){
    val doctorController = DoctorController()
    val patientController = PatientController()
    doctorController.initFromJson()

    doctorController.getAllDoctors().forEach{doctor 
        -> println("Name: ${doctor.name}, Speciality: ${doctor.specialty}, Salary: ${doctor.salary}")} 

    iterateOnPatientList(patientController)
}

fun iterateOnPatientList(controller: PatientController) {
    // Load patients from JSON
    controller.loadPatientsFromJson()

    // Example: get a patient by CC safely
    val maybePatient = controller.getPatientByCC("20049") // returns Patient?
    maybePatient?.let { patient ->
        println("Patient City: ${patient.city.name}")
    } ?: println("Patient with CC 20049 not found")

    // Iterate over all patients
    controller.getAllPatients().forEach { patient ->
        println("Natal City: ${patient.city.name}")
    }
}
