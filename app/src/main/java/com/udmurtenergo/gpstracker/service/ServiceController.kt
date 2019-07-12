package com.udmurtenergo.gpstracker.service

import android.content.Context
import com.udmurtenergo.gpstracker.R
import com.udmurtenergo.gpstracker.database.model.LogData
import com.udmurtenergo.gpstracker.interactor.NetworkInteractor
import com.udmurtenergo.gpstracker.interactor.PreferenceInteractor
import com.udmurtenergo.gpstracker.interactor.gps.GpsInteractor
import com.udmurtenergo.gpstracker.repository.RepositoryLocation
import com.udmurtenergo.gpstracker.repository.RepositoryLog
import com.udmurtenergo.gpstracker.utils.AlarmTimer
import com.udmurtenergo.gpstracker.utils.Settings
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

class ServiceController(
    private val context: Context,
    private var service: ServiceContract.Service?,
    private val repositoryLocation: RepositoryLocation,
    private val repositoryLog: RepositoryLog,
    private val gpsInteractor: GpsInteractor,
    private val networkInteractor: NetworkInteractor,
    private val preferenceInteractor: PreferenceInteractor,
    private val alarmTimer: AlarmTimer,
    private val serviceNotification: ServiceNotification) : ServiceContract.Controller, AlarmTimer.Listener {

    private val compositeDisposable = CompositeDisposable()

    override fun onStartCommand() {
        compositeDisposable.add(preferenceInteractor.load()
            // Ошибка будет зафиксирована в логах, приложение упадет
            .doOnError { throwable ->
                repositoryLog.insert(
                    LogData(context.getString(R.string.error_to_read_settings), throwable.message!!, Date())
                )
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(Consumer { this.execute(it) })
        )
    }

    override fun onDestroy() {
        stopExecute()
    }

    private fun execute(settings: Settings) {
        gpsInteractor.callLocationUpdates(settings.updateInterval, settings.smallestDisplacement,
            settings.minAccuracy, settings.minSatellitesCount, settings.minSnr)

        compositeDisposable.add(gpsInteractor.observeLocation()
            .subscribe { location -> service?.onLocationChanged(location)} )

        compositeDisposable.add(gpsInteractor.observeGpsStatus()
            .subscribe { gpsStatus -> service?.onGpsStatusChanged(gpsStatus)} )

        compositeDisposable.add(gpsInteractor.observeFullLocation()
            .observeOn(Schedulers.io())
            .doOnNext { fullLocation -> repositoryLocation.insert(fullLocation) }
            .doOnError { throwable ->
                repositoryLog.insert(
                    LogData(context.getString(R.string.error_to_insert_location), throwable.message!!, Date())
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { fullLocation -> service?.onFilteredLocationChanged(fullLocation)} )

        networkInteractor.initialize(service!!.getDeviceImei(), settings.serverIp)
        alarmTimer.turnOn(this, settings.networkUpdateInterval)
        serviceNotification.show()
    }

    private fun stopExecute() {
        service = null
        gpsInteractor.removeLocationUpdates()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        alarmTimer.turnOff()
        serviceNotification.hide()
    }

    override fun onTimerClick() {
        if (service != null && service!!.networkIsOnline()) {
            compositeDisposable.add(repositoryLocation.getAllOnce()
                .flatMapObservable { list -> Observable.fromIterable(list) }
                .doOnNext { fullLocation -> networkInteractor.sendData(fullLocation, Date()) }
                .doOnError { throwable ->
                    repositoryLog.insert(
                        LogData(context.getString(R.string.network_error), throwable.message!!, Date())
                    )
                }
                .doOnNext { fullLocation -> repositoryLocation.delete(fullLocation) }
                .subscribeOn(Schedulers.io())
                .subscribe({ }, { }))
        }
    }
}