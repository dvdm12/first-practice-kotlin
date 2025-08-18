package models

class State(
    val name: String,
    val cities: MutableList<City> = mutableListOf()
)
