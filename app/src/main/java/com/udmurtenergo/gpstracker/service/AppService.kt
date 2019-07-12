package com.udmurtenergo.gpstracker.service

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.GpsStatus
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Binder
import android.os.IBinder
import android.telephony.TelephonyManager
import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.database.model.FullLocation
import com.udmurtenergo.gpstracker.di.module.ServiceModule
import com.udmurtenergo.gpstracker.interactor.gps.GpsListener

import javax.inject.Inject

class AppService : Service(), ServiceContract.Service {
    @Inject
    lateinit var controller: ServiceContract.Controller
    private val binder = ServiceBinder()
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var telephonyManager: TelephonyManager
    private var listener: GpsListener? = null // presenter

    override fun onCreate() {
        super.onCreate()
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        App.instance.injector.getServiceComponentInstance(ServiceModule(this)).inject(this)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        controller.onStartCommand()
        return Service.START_STICKY // Служба будет перезапущена при нехватке памяти
    }

    override fun onDestroy() {
        controller.onDestroy()
        super.onDestroy()
    }

    inner class ServiceBinder : Binder() {
        val service: ServiceContract.Service
            get() = this@AppService
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun networkIsOnline(): Boolean {
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    @SuppressLint("MissingPermission")
    override fun getDeviceImei() = telephonyManager.deviceId!!

    override fun registerListener(listener: GpsListener){
        this.listener = listener
    }

    override fun unRegisterListener() {
        listener = null
    }

    override fun onLocationChanged(location: Location) {
        listener?.onLocationChanged(location)
    }

    override fun onGpsStatusChanged(gpsStatus: GpsStatus) {
        listener?.onGpsStatusChanged(gpsStatus)
    }

    override fun onFilteredLocationChanged(fullLocation: FullLocation) {
        listener?.onFilteredLocationChanged(fullLocation)
    }
}
