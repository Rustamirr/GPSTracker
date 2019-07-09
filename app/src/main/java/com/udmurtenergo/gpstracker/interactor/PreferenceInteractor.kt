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
            val settings = Settings(
                sh.getBoolean(context.getString(R.string.key_boot_service), false),
                Integer.valueOf(sh.getString(context.getString(R.string.key_min_snr), "")!!),
                Integer.valueOf(sh.getString(context.getString(R.string.key_min_accuracy), "")!!),
                Integer.valueOf(sh.getString(context.getString(R.string.key_min_satellites_count),"")!!),
                Integer.valueOf(sh.getString(context.getString(R.string.key_gps_update_interval),"")!!),
                Integer.valueOf(sh.getString(context.getString(R.string.key_smallest_displacement),"")!!),
                sh.getString(context.getString(R.string.key_server_ip), "")!!,
                Integer.valueOf(sh.getString(context.getString(R.string.key_network_update_interval),"")!!)
            )
            settings
        }
    }
}
