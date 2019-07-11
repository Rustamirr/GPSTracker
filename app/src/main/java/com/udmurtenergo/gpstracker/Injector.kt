package com.udmurtenergo.gpstracker

import com.udmurtenergo.gpstracker.di.component.AppComponent
import com.udmurtenergo.gpstracker.di.component.DaggerAppComponent
import com.udmurtenergo.gpstracker.di.component.MainActivityComponent
import com.udmurtenergo.gpstracker.di.component.ServiceComponent
import com.udmurtenergo.gpstracker.di.module.AppModule
import com.udmurtenergo.gpstracker.di.module.DatabaseModule
import com.udmurtenergo.gpstracker.di.module.ServiceModule

class Injector(app: App){
    private val databaseName = "GpsTracker"
    val appComponent: AppComponent
    var mainActivityComponent: MainActivityComponent? = null

    init {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .databaseModule(DatabaseModule(databaseName))
            .build()
    }

    // Every time create new service
    fun getServiceComponentInstance(serviceModule: ServiceModule): ServiceComponent = appComponent.getServiceComponent(serviceModule)

    // Main activity
    fun getMainActivityComponentInstance(): MainActivityComponent {
        if (mainActivityComponent == null) {
            mainActivityComponent = appComponent.getMainActivityComponent()
        }
        return mainActivityComponent!!
    }

    fun clearMainActivityComponent() {
        mainActivityComponent = null
    }
}