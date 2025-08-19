package org.example.hospital

import org.example.hospital.controller.DoctorController
import org.example.hospital.controller.PatientController
import models.Patient
import javax.print.Doc
    
fun main(){
    val doctorController = DoctorController()
    val patientController = PatientController()
    doctorController.initFromJson()

    doctorController.getAllDoctors().forEach{doctor 
        -> println("Name: ${doctor.name}, Speciality: ${doctor.specialty}, Salary: ${doctor.salary}")}

    println("watching each doctor by speciality")
    var specialty = "Psiquiatría"
    var amount = doctorController.getDoctorsAmountBySpeciality(specialty)
    println("Speciality: ${specialty}, Amount of doctors: ${amount}}")
    
    var totalSalary = doctorController.calculateAllSalary()

    println("Total of salary: $${totalSalary}")

    patientController.loadPatientsFromJson()

    patientController.showPercentagePatientsByGender()
}

fun getSalaryBySpeciality(doctorController:DoctorController){
    for(doctor in doctorController.getAllDoctors()){
        println(doctorController.calculateDoctorsSalaryBySpeciality(doctor.specialty))
    }
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
