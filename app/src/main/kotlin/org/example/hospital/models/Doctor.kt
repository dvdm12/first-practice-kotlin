package models

import java.time.LocalDate
import models.Patient
import models.Person

class Doctor(
    name: String,
    cc: String,
    gender: String,
    email: String,
    val licenseNumber: String,
    var salary: Int, 
    val specialty: String,
    val yearJoined: LocalDate,
    var isActivated: Boolean = true,
    val patients: MutableList<Patient> = mutableListOf()
) : Person(name, cc, gender, email)
