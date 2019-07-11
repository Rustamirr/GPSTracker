/*package com.udmurtenergo.gpstracker.view.activity_main.map_fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.database.model.FullLocation
import com.udmurtenergo.gpstracker.database.model.LocationData
import com.udmurtenergo.gpstracker.database.model.Satellite

class MapFragment : SupportMapFragment(), MapFragmentContract.View, OnMapReadyCallback {

    var presenter: MapFragmentContract.Presenter
    private var googleMap: GoogleMap? = null

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        presenter = ViewModelProviders.of(this).get(MapFragmentPresenter::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMapAsync(this)
        presenter.onViewCreated(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onDestroyView() {
        presenter.onDestroyView()
        super.onDestroyView()
    }

    override fun gpsPermissionGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            App.getInstance(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun requestGpsPermission() {
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ACCESS_FINE_LOCATION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ACCESS_FINE_LOCATION_REQUEST_CODE -> {
                if (grantResults.size > 0) {
                    presenter.onGpsRequestPermissionsResult(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                }
            }
        }
    }

    override fun setMap(
        enableZoomControls: Boolean, enabledTiltGesture: Boolean,
        enableCompass: Boolean, showCurrentLocationControl: Boolean
    ) {
        googleMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap!!.uiSettings.isZoomControlsEnabled = enableZoomControls
        googleMap!!.uiSettings.isTiltGesturesEnabled = enabledTiltGesture
        googleMap!!.uiSettings.isCompassEnabled = enableCompass
        googleMap!!.uiSettings.isMyLocationButtonEnabled = showCurrentLocationControl
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        presenter.onMapReady()
    }

    @SuppressLint("MissingPermission")
    override fun setCurrentLocationEnabled(enabled: Boolean) {
        googleMap!!.isMyLocationEnabled = enabled
    }

    override fun drawTrack(list: List<FullLocation>) {
        googleMap!!.clear()
        val line = PolylineOptions()
        for ((locationData, satellites) in list) {
            val latLng = LatLng(locationData.latitude, locationData.longitude)
            line.add(latLng)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.title("Accuracy(m): " + locationData.accuracy)


            val sb = StringBuilder()
            sb.append("SNR:")
            for ((_, snr) in satellites) {
                sb.append(snr)
                sb.append("|")
            }
            markerOptions.snippet(sb.toString())
            googleMap!!.addMarker(markerOptions)
        }
        line.width(12f)
        line.color(Color.RED)
        line.geodesic(true)
        googleMap!!.addPolyline(line)
    }

    override fun setTitle(title: String) {
        super.onStart()
        if (activity != null) {
            activity!!.title = title
        }
    }

    companion object {
        private val ACCESS_FINE_LOCATION_REQUEST_CODE = 1

        override fun newInstance(): MapFragment {
            return MapFragment()
        }
    }
}*/
