package org.example.hospital

import org.example.hospital.controller.DoctorController
    
fun main(){
    val doctorController = DoctorController()
    doctorController.initFromJson()

    doctorController.getAllDoctors().forEach{doctor -> println(doctor.name)} 
}