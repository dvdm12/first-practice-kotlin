package models

class Country(
    val name: String,
    val states: MutableList<State> = mutableListOf()
)
