package com.udmurtenergo.gpstracker.database.model

import android.location.Location
import java.util.Date

data class LocationData(
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val date: Date,
    val speed: Float,
    val accuracy: Float,
    val altitude: Double,
    val bearing: Float){

    constructor(location: Location) : this(
        0,
        location.latitude,
        location.longitude,
        Date(location.time),
        location.speed,
        location.accuracy,
        location.altitude,
        location.bearing)
}