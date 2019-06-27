package com.udmurtenergo.gpstracker.database.model

data class FullLocation(
    val locationData: LocationData,
    val satellites: List<Satellite>
)
