package com.udmurtenergo.gpstracker.database.dao

import androidx.room.*
import com.udmurtenergo.gpstracker.database.entity.EntityLocation
import com.udmurtenergo.gpstracker.database.entity.EntitySatellite
import com.udmurtenergo.gpstracker.database.model.FullLocation
import com.udmurtenergo.gpstracker.database.model.LocationData
import com.udmurtenergo.gpstracker.database.model.Satellite
import com.udmurtenergo.gpstracker.database.request.RequestLocation
import io.reactivex.Observable
import io.reactivex.Single
import java.util.ArrayList
import java.util.Date

@Dao
abstract class LocationDao {

    @Transaction
    open fun insert(fullLocation: FullLocation) {
        val id = insertEntityLocation(EntityLocation(fullLocation.locationData))

        val entitySatellites= fullLocation.satellites.map { EntitySatellite(it.snr, id) }
        insertEntitySatellites(entitySatellites)
    }

    fun delete(fullLocation: FullLocation) {
        deleteEntityLocation(EntityLocation(fullLocation.locationData))
    }

    fun getAll(): Observable<List<FullLocation>> {
        return requestLocationsDesc().map { t -> requestLocationsToFullLocations(t) }
    }

    fun getAllOnce(): Single<List<FullLocation>> {
        return requestLocations().map { t ->  requestLocationsToFullLocations(t) }
    }

    private fun requestLocationsToFullLocations(requestLocations: List<RequestLocation>) =
        requestLocations.map { createFullLocation(it.entityLocation, it.entitySatellites) }

    private fun createFullLocation(entityLocation: EntityLocation, entitySatellites: List<EntitySatellite>): FullLocation {
        val locationData = LocationData(
            entityLocation.id, entityLocation.latitude,
            entityLocation.longitude, Date(entityLocation.dateMillis), entityLocation.speed,
            entityLocation.accuracy, entityLocation.altitude, entityLocation.bearing
        )
        val satellites = entitySatellites.map {Satellite(it.id, it.snr, locationData) }

        return FullLocation(locationData, satellites)
    }

    @Insert
    abstract fun insertEntityLocation(entityLocation: EntityLocation): Long

    @Insert
    abstract fun insertEntitySatellites(entitySatellites: List<EntitySatellite>)

    @Delete
    abstract fun deleteEntityLocation(entityLocation: EntityLocation)


    @Transaction
    @Query("SELECT * FROM Locations")
    abstract fun requestLocations(): Single<List<RequestLocation>>

    @Transaction
    @Query("SELECT * FROM Locations ORDER BY Locations.dateMillis DESC")
    abstract fun requestLocationsDesc(): Observable<List<RequestLocation>>
}