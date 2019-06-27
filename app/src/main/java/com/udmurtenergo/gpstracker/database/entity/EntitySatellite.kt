package com.udmurtenergo.gpstracker.database.entity

import androidx.room.*
import com.udmurtenergo.gpstracker.database.model.Satellite

@Entity(
    tableName = "Satellites",
    foreignKeys = [ForeignKey(
        entity = EntityLocation::class,
        parentColumns = ["id"],
        childColumns = ["locationId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("locationId")]
)
data class EntitySatellite (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val snr: Float,
    val locationId: Long){

    @Ignore
    constructor(snr: Float, locationId: Long): this(
        0,
        snr,
        locationId
    )

    @Ignore
    constructor(satellite: Satellite): this(
        satellite.id,
        satellite.snr,
        satellite.location.id
    )
}