package com.udmurtenergo.gpstracker.interactor.gps

import android.annotation.SuppressLint
import android.content.Context
import android.location.GpsStatus
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.database.model.FullLocation
import com.udmurtenergo.gpstracker.database.model.LocationData
import com.udmurtenergo.gpstracker.database.model.Satellite
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import java.util.*

class GpsInteractor(
    private val googleApiClient: GoogleApiClient,
    private val locationRequest: LocationRequest): GoogleApiClient.ConnectionCallbacks, LocationListener, GpsStatus.Listener {

    private val locationManager = App.instance.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    private lateinit var gpsStatus: GpsStatus
    private val locationSubject = PublishSubject.create<Location>()
    private val gpsStatusSubject = PublishSubject.create<GpsStatus>()
    private lateinit var fullLocationObservable: Observable<FullLocation>

    @SuppressLint("MissingPermission")
    fun callLocationUpdates(updateInterval: Int, smallestDisplacement: Int, minAccuracy: Int, minSatellitesCount: Int, minSnr: Int) {
        locationRequest.interval = (updateInterval * 1000).toLong()
        locationRequest.fastestInterval = (updateInterval * 1000).toLong()
        locationRequest.smallestDisplacement = smallestDisplacement.toFloat()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        googleApiClient.registerConnectionCallbacks(this)
        locationManager.addGpsStatusListener(this)

        fullLocationObservable = initFullLocationObservable(minAccuracy, minSatellitesCount, minSnr)
        googleApiClient.connect()
    }

    fun removeLocationUpdates() {
        locationManager.removeGpsStatusListener(this)
        if (googleApiClient.isConnected) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this)
            googleApiClient.disconnect()
        }
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {
        // Устаревший метод запроса gps следует заменить после выхода 12 версии play services и
        // использовать fusedClient
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this)
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onLocationChanged(location: Location) {
        locationSubject.onNext(location)
    }

    @SuppressLint("MissingPermission")
    override fun onGpsStatusChanged(event: Int) {
        if (event != GpsStatus.GPS_EVENT_SATELLITE_STATUS) {
            return
        }
        gpsStatus = locationManager.getGpsStatus(gpsStatus)
        gpsStatusSubject.onNext(gpsStatus)
    }

    private fun initFullLocationObservable(minAccuracy: Int, minSatellitesCount: Int, minSnr: Int): Observable<FullLocation> {
        val locationObservable = locationSubject
            .filter { location -> location.accuracy <= minAccuracy }

        val snrObservable = gpsStatusSubject
            .map { gpsStatus ->
                val snrList = ArrayList<Float>()
                gpsStatus.satellites.forEach {
                    if (it.usedInFix() && it.snr >= minSnr) {
                        snrList.add(it.snr)
                    }
                }
                snrList
            }.filter { list -> list.size >= minSatellitesCount }

        return locationObservable.withLatestFrom(snrObservable, BiFunction {location: Location, snrList: List<Float> ->
            val locationData = LocationData(location)
            val satellites = snrList.map{ Satellite(it, locationData) }
            FullLocation(locationData, satellites)
        })
    }

    fun observeLocation(): Observable<Location> {
        return locationSubject
    }

    fun observeGpsStatus(): Observable<GpsStatus> {
        return gpsStatusSubject
    }

    fun observeFullLocation(): Observable<FullLocation> {
        return fullLocationObservable
    }
}