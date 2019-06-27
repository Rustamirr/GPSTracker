package com.udmurtenergo.gpstracker.database.request

import androidx.room.Embedded
import androidx.room.Relation
import com.udmurtenergo.gpstracker.database.entity.EntityLocation
import com.udmurtenergo.gpstracker.database.entity.EntitySatellite

class RequestLocation (
    @Embedded
    val entityLocation: EntityLocation,

    @Relation(parentColumn = "id", entityColumn = "locationId", entity = EntitySatellite::class)
    val entitySatellites: List<EntitySatellite>
)
