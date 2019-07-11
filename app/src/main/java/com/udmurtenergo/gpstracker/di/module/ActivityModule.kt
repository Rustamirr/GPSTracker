package com.udmurtenergo.gpstracker.di.module

import com.udmurtenergo.gpstracker.di.ActivityScope
/*import com.udmurtenergo.gpstracker.view.activity_main.location_fragment.LocationAdapter
import com.udmurtenergo.gpstracker.view.activity_main.log_fragment.LogAdapter*/
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

@Module
class ActivityModule {

    @ActivityScope
    @Provides
    fun provideCicerone(): Cicerone<Router> = Cicerone.create()

    /*@Provides
    fun provideLocationAdapter() = LocationAdapter()

    @Provides
    fun provideLogAdapter() = LogAdapter()*/
}
