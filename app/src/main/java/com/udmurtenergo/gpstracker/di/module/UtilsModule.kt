package com.udmurtenergo.gpstracker.di.module

import com.udmurtenergo.gpstracker.utils.AlarmTimer
import dagger.Module
import dagger.Provides

@Module
class UtilsModule {
    @Provides
    fun provideAlarmTimer() = AlarmTimer()
}
