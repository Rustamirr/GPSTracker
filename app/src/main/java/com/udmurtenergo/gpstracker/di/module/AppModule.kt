package com.udmurtenergo.gpstracker.di.module

import android.content.Context
import com.udmurtenergo.gpstracker.interactor.PreferenceInteractor
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {
    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun providePreferenceInteractor(context: Context): PreferenceInteractor = PreferenceInteractor(context)
}
