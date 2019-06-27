package com.udmurtenergo.gpstracker.interactor.gps

import android.location.GpsStatus
import android.location.Location
import com.udmurtenergo.gpstracker.database.model.FullLocation

interface GpsListener {
    fun onLocationChanged(location: Location)
    fun onGpsStatusChanged(gpsStatus: GpsStatus)
    fun onFilteredLocationChanged(fullLocation: FullLocation)
}
