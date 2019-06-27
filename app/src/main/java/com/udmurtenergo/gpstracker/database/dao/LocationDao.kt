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

        // Satellites
        val entitySatellites = ArrayList<EntitySatellite>(fullLocation.satellites.size)
        for (satellite in fullLocation.satellites) {
            entitySatellites.add(EntitySatellite(satellite.snr, id))
        }
        insertEntitySatellites(entitySatellites)
    }

    fun delete(fullLocation: FullLocation) {
        deleteEntityLocation(EntityLocation(fullLocation.locationData))
    }

    fun getAll(): Observable<List<FullLocation>> {
        return requestLocationsDesc().map { t -> requestLocationsToFullLocations(t) }
    }

    fun getAllOnce(): Single<List<FullLocation>> {
        return requestLocations().map { t -> requestLocationsToFullLocations(t) }
    }

    private fun requestLocationsToFullLocations(requestLocations: List<RequestLocation>): List<FullLocation> {
        val fullLocations = ArrayList<FullLocation>(requestLocations.size)
        for (requestLocation in requestLocations) {
            // Location
            val entityLocation = requestLocation.entityLocation

            val locationData = LocationData(
                entityLocation.id, entityLocation.latitude,
                entityLocation.longitude, Date(entityLocation.dateMillis), entityLocation.speed,
                entityLocation.accuracy, entityLocation.altitude, entityLocation.bearing
            )

            // Satellites
            val entitySatellites = requestLocation.entitySatellites
            val satellites = ArrayList<Satellite>(entitySatellites.size)
            for ((id, snr) in entitySatellites) {
                satellites.add(Satellite(id, snr, locationData))
            }
            fullLocations.add(FullLocation(locationData, satellites))
        }
        return fullLocations
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