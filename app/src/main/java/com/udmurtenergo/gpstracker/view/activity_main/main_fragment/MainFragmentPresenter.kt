/*package com.udmurtenergo.gpstracker.view.activity_main.main_fragment

import android.Manifest
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.location.GpsSatellite
import android.location.GpsStatus
import android.location.Location
import android.os.Build
import android.os.IBinder
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.database.model.FullLocation
import com.udmurtenergo.gpstracker.database.model.LocationData
import com.udmurtenergo.gpstracker.database.model.Satellite
import com.udmurtenergo.gpstracker.interactor.gps.GpsListener
import com.udmurtenergo.gpstracker.service.AppService
import com.udmurtenergo.gpstracker.service.ServiceContract

import java.text.SimpleDateFormat
import java.util.Locale

class MainFragmentPresenter : ViewModel(), MainFragmentContract.Presenter, ServiceConnection, GpsListener {
    private var service: ServiceContract.Service? = null
    private var view: MainFragmentContract.View? = null
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss.SSS ZZZZZ", Locale.US)

    override fun onViewCreated(view: MainFragmentContract.View) {
        this.view = view
        view.setTitle(App.getInstance().getString(R.string.app_name))
    }

    override fun onStart() {
        bindService()
    }

    override fun onStop() {
        unbindService()
    }

    override fun onDestroyView() {
        view = null
    }

    override fun startStopService() {
        // Stop service
        if (service != null) {
            stopService()
            service = null
            return
        }
        // Check Google Play Services (Need GPS fused provider)
        if (!playServicesAvailable()) {
            view!!.setSwitchServiceChecked(false)
            view!!.showMessage(App.getInstance().getString(R.string.google_play_service_is_not_installed))
            return
        }
        // Check permissions
        if (!permissionsGranted()) {
            view!!.requestPermissions()
            return
        }
        startService()
    }

    private fun startService() {
        val intent = Intent(App.getInstance(), AppService::class.java)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            App.getInstance().startForegroundService(intent)
        } else {
            App.getInstance().startService(intent)
        }
        App.getInstance().bindService(intent, this, 0)
    }

    private fun stopService() {
        val intent = Intent(App.getInstance(), AppService::class.java)
        App.getInstance().getApplicationContext().stopService(intent)
    }

    private fun bindService() {
        val intent = Intent(App.getInstance(), AppService::class.java)
        App.getInstance().bindService(intent, this, 0) // 0 - подключение произойдет если сервис запущен
    }

    private fun unbindService() {
        disconnectService()
        App.getInstance().unbindService(this)
    }

    override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
        service = (iBinder as AppService.ServiceBinder).service
        service!!.registerListener(this)
        if (view != null) {
            view!!.setSwitchServiceChecked(true)
        }
    }

    override fun onServiceDisconnected(name: ComponentName) {
        disconnectService()
    }

    private fun disconnectService() {
        if (service != null) {
            service!!.unRegisterListener()
        }
        if (view != null) {
            view!!.setSwitchServiceChecked(false)
        }
    }


    override fun onLocationChanged(location: Location) {
        if (view != null) {
            view!!.resetChronometer()
            val text = App.getInstance().getString(R.string.latitude) + ": " + location.latitude + "\n" +
                    App.getInstance().getString(R.string.longitude) + ": " + location.longitude + "\n" +
                    App.getInstance().getString(R.string.date) + ": " + dateFormat.format(location.time) + "\n" +
                    App.getInstance().getString(R.string.speed) + ": " + Math.round(location.speed * 3.6) + "\n" +
                    App.getInstance().getString(R.string.accuracy) + ": " + location.accuracy + "\n" +
                    App.getInstance().getString(R.string.altitude) + ": " + location.altitude + "\n" +
                    App.getInstance().getString(R.string.bearing) + ": " + location.bearing
            view!!.setLocationText(text)
        }
    }

    override fun onGpsStatusChanged(gpsStatus: GpsStatus) {
        if (view != null) {
            val sb = StringBuilder()
            var satCount = 0
            val satellites = gpsStatus.satellites
            for (satellite in satellites) {
                if (satellite.usedInFix()) {
                    satCount++
                    sb.append(satellite.snr)
                    sb.append(" | ")
                }
            }
            val text = App.getInstance().getString(R.string.satellites_count) + ": " + satCount + "\n" +
                    App.getInstance().getString(R.string.snr) + ":\n" + sb.toString()
            view!!.setSatellitesText(text)
        }
    }

    override fun onFilteredLocationChanged(fullLocation: FullLocation) {
        if (view != null) {
            // Location
            val locationData = fullLocation.locationData
            var text = "Filtered:\n" +
                    App.getInstance().getString(R.string.latitude) + ": " + locationData.latitude + "\n" +
                    App.getInstance().getString(R.string.longitude) + ": " + locationData.longitude + "\n" +
                    App.getInstance().getString(R.string.date) + ": " + dateFormat.format(locationData.date) + "\n" +
                    App.getInstance().getString(R.string.speed) + ": " + Math.round(locationData.speed * 3.6) + "\n" +
                    App.getInstance().getString(R.string.accuracy) + ": " + locationData.accuracy + "\n" +
                    App.getInstance().getString(R.string.altitude) + ": " + locationData.altitude + "\n" +
                    App.getInstance().getString(R.string.bearing) + ": " + locationData.bearing + "\n"

            // Satellites
            val satellites = fullLocation.satellites
            val sb = StringBuilder()
            for ((_, snr) in satellites) {
                sb.append(snr)
                sb.append(" | ")
            }
            text += App.getInstance().getString(R.string.satellites_count) + ": " + satellites.size + "\n" +
                    App.getInstance().getString(R.string.snr) + ":\n" + sb.toString()

            view!!.setFilteredLocationText(text)
        }
    }


    private fun playServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(App.getInstance().getApplicationContext())
        return resultCode == ConnectionResult.SUCCESS
    }

    private fun permissionsGranted(): Boolean {
        return ActivityCompat.checkSelfPermission(
            App.getInstance(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            App.getInstance(),
            Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(granted: Boolean) {
        if (granted) {
            startService()
        } else {
            view!!.setSwitchServiceChecked(false)
        }
    }

}*/
