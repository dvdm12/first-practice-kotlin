package org.example.hospital

import org.example.hospital.controller.DoctorController
import org.example.hospital.controller.PatientController
import org.example.hospital.view.HospitalView

fun main() {
    val doctorController = DoctorController()
    val patientController = PatientController()

    doctorController.initFromJson()
    patientController.loadPatientsFromJson()

    val hospitalView = HospitalView(doctorController, patientController)

    hospitalView.start()
}
