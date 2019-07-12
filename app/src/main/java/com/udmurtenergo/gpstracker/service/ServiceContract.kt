package com.udmurtenergo.gpstracker.service

import com.udmurtenergo.gpstracker.interactor.gps.GpsListener

interface ServiceContract {
    interface Service : GpsListener {
        fun getDeviceImei(): String
        fun registerListener(listener: GpsListener)
        fun unRegisterListener()
        fun networkIsOnline(): Boolean
    }
    interface Controller {
        fun onStartCommand()
        fun onDestroy()
    }
}
