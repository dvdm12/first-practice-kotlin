package models

import models.State

class Country(
    val name: String,
    val states: MutableList<State> = mutableListOf()
)
