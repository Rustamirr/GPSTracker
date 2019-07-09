package com.udmurtenergo.gpstracker.interactor

import android.content.Context
import androidx.preference.PreferenceManager
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.utils.Settings
import io.reactivex.Single

class PreferenceInteractor(private val context: Context) {
    fun initialize() {
        PreferenceManager.setDefaultValues(context, R.xml.settings, false)
    }

    fun load(): Single<Settings> {
        return Single.fromCallable {
            val sh = PreferenceManager.getDefaultSharedPreferences(context)
            val settings = Settings()
            settings.isBootService = sh.getBoolean(context.getString(R.string.key_boot_service), settings.isBootService)
            settings.minSnr = Integer.valueOf(sh.getString(context.getString(R.string.key_min_snr), ""))
            settings.minAccuracy = Integer.valueOf(sh.getString(context.getString(R.string.key_min_accuracy), ""))
            settings.minSatellitesCount = Integer.valueOf(sh.getString(context.getString(R.string.key_min_satellites_count),""))
            settings.updateInterval = Integer.valueOf(sh.getString(context.getString(R.string.key_gps_update_interval),""))
            settings.smallestDisplacement = Integer.valueOf(sh.getString(context.getString(R.string.key_smallest_displacement),""))
            settings.serverIp = sh.getString(context.getString(R.string.key_server_ip), "")
            settings.networkUpdateInterval = Integer.valueOf(sh.getString(context.getString(R.string.key_network_update_interval),""))
            settings
        }
    }
}
