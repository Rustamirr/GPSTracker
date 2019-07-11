package com.udmurtenergo.gpstracker.di.component

import com.udmurtenergo.gpstracker.di.module.AppModule
import com.udmurtenergo.gpstracker.di.module.DatabaseModule
import com.udmurtenergo.gpstracker.di.module.ServiceModule
import com.udmurtenergo.gpstracker.interactor.PreferenceInteractor
import com.udmurtenergo.gpstracker.repository.RepositoryLog
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DatabaseModule::class])
interface AppComponent {
    fun getPreferenceInteractor(): PreferenceInteractor
    fun getRepositoryLog(): RepositoryLog

    fun getServiceComponent(serviceModule: ServiceModule): ServiceComponent
    fun getMainActivityComponent(): MainActivityComponent
}
