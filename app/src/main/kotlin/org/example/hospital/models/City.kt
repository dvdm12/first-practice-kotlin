package models

class City(
    val name: String,
    val postalCode: String,
    val streets: MutableList<Street> = mutableListOf()
)
