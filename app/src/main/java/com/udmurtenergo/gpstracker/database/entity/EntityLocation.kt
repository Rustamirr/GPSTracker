package com.udmurtenergo.gpstracker.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.udmurtenergo.gpstracker.database.model.LocationData
import java.util.Calendar

@Entity(tableName = "Locations")
data class EntityLocation (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val dateMillis: Long,
    val dateTimeZoneRawOfSet: Int,
    val speed: Float,
    val accuracy: Float,
    val altitude: Double,
    val bearing: Float){

    @Ignore
    constructor(locationData: LocationData) : this(
        locationData.id,
        locationData.latitude,
        locationData.longitude,
        locationData.date.time,
        Calendar.getInstance().timeZone.rawOffset,
        locationData.speed,
        locationData.accuracy,
        locationData.altitude,
        locationData.bearing)
}