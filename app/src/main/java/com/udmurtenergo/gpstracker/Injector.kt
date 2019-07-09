package com.udmurtenergo.gpstracker

class Injector internal constructor(app: App) {
    //lateinit var appComponent: AppComponent
    //lateinit var mainActivityComponent: MainActivityComponent

    companion object {
        private val DATABASE_NAME = "GpsTracker"
    }

    /*init {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .databaseModule(DatabaseModule(DATABASE_NAME))
            .build()
    }

    // Every time create new service
    fun getServiceComponent(serviceModule: ServiceModule): ServiceComponent {
        return appComponent.getServiceComponent(serviceModule)
    }

    // Main activity
    fun getMainActivityComponent(): MainActivityComponent {
        if (mainActivityComponent == null) {
            mainActivityComponent = appComponent.getMainActivityComponent()
        }
        return mainActivityComponent
    }

    fun clearMainActivityComponent() {
        mainActivityComponent = null
    }  */
}