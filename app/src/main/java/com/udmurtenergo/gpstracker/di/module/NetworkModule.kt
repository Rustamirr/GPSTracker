package com.udmurtenergo.gpstracker.di.module

import com.udmurtenergo.gpstracker.interactor.NetworkInteractor
import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideNetworkInteractor(): NetworkInteractor = NetworkInteractor()
}
