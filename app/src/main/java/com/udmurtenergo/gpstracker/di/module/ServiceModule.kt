package com.udmurtenergo.gpstracker.di.module

import android.content.Context
import com.udmurtenergo.gpstracker.interactor.NetworkInteractor
import com.udmurtenergo.gpstracker.interactor.PreferenceInteractor
import com.udmurtenergo.gpstracker.interactor.gps.GpsInteractor
import com.udmurtenergo.gpstracker.repository.RepositoryLocation
import com.udmurtenergo.gpstracker.repository.RepositoryLog
import com.udmurtenergo.gpstracker.service.ServiceContract
import com.udmurtenergo.gpstracker.service.ServiceController
import com.udmurtenergo.gpstracker.service.ServiceNotification
import com.udmurtenergo.gpstracker.utils.AlarmTimer
import dagger.Module
import dagger.Provides

@Module
class ServiceModule(private val service: ServiceContract.Service) {

    @Provides
    fun provideAppNotification(): ServiceNotification = ServiceNotification(service)

    @Provides
    fun provideController(context: Context, repositoryLocation: RepositoryLocation, repositoryLog: RepositoryLog,
        gpsInteractor: GpsInteractor, networkInteractor: NetworkInteractor, preferenceInteractor: PreferenceInteractor,
        alarmTimer: AlarmTimer, serviceNotification: ServiceNotification): ServiceContract.Controller =

        ServiceController(context, service, repositoryLocation, repositoryLog, gpsInteractor, networkInteractor,
            preferenceInteractor, alarmTimer, serviceNotification)
}
