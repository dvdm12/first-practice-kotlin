package models

import models.Street

class City(
    val name: String,
    val postalCode: String,
    val streets: MutableList<Street> = mutableListOf()
)
