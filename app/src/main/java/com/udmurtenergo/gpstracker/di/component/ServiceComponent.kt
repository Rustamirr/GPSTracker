package com.udmurtenergo.gpstracker.di.component

import com.udmurtenergo.gpstracker.di.module.GpsModule
import com.udmurtenergo.gpstracker.di.module.NetworkModule
import com.udmurtenergo.gpstracker.di.module.ServiceModule
import com.udmurtenergo.gpstracker.di.module.UtilsModule
import com.udmurtenergo.gpstracker.service.AppService
import dagger.Subcomponent

@Subcomponent(modules = [ServiceModule::class, GpsModule::class, NetworkModule::class, UtilsModule::class])
interface ServiceComponent {
    fun inject(service: AppService)
}
