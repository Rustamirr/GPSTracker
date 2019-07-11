package com.udmurtenergo.gpstracker.utils

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
        App.instance.injector.appComponent.getPreferenceInteractor().load()
            .doOnError { throwable ->
                App.instance.injector.appComponent.getRepositoryLog().insert(
                    LogData(App.instance.resources.getString(R.string.error_to_read_settings),
                        throwable.message!!, Date()))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { settings ->
                if (settings.isBootService) {
                    val bootIntent = Intent(App.instance, AppService::class.java)
                    App.instance.startService(bootIntent)
                }
            }
    }
}
