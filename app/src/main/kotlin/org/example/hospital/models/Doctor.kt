package models

import java.time.LocalDate

class Doctor(
    name: String,
    cc: String,
    gender: String,
    email: String,
    val licenseNumber: String, // único
    val specialty: String,
    val yearJoined: LocalDate,
    var isActivated: Boolean = true,
    val patients: MutableList<Patient> = mutableListOf()
) : Person(name, cc, gender, email)
