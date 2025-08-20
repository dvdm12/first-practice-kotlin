package models

class Patient(
    name: String,
    cc: String,
    gender: String,
    email: String,
    val phone: String,
    val city: City
) : Person(name, cc, gender, email)
