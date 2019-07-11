/*package com.udmurtenergo.gpstracker.utils

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.udmurtenergo.gpstracker.App
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.database.model.LogData
import com.udmurtenergo.gpstracker.service.AppService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import java.util.Date

class BootReceiver : BroadcastReceiver() {

    @SuppressLint("CheckResult")
    override fun onReceive(context: Context, intent: Intent) {
        App.getInstance().injector.getAppComponent().getPreferenceInteractor().load()
            .doOnError({ throwable ->
                App.getInstance().getInjector().getAppComponent().getRepositoryLog().insert(
                    LogData(
                        App.getInstance().resources.getString(R.string.error_to_read_settings),
                        throwable.getMessage(),
                        Date()
                    )
                )
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ settings ->
                if (settings.isBootService()) {
                    val bootIntent = Intent(App.getInstance(), AppService::class.java)
                    App.getInstance().startService(bootIntent)
                }
            })
    }
}*/
