package com.udmurtenergo.gpstracker.di.module

import android.content.Context
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.udmurtenergo.gpstracker.interactor.gps.GpsInteractor
import dagger.Module
import dagger.Provides

@Module
class GpsModule {
    @Provides
    fun provideGoogleApiClient(context: Context) =
        GoogleApiClient.Builder(context)
            .addApi(LocationServices.API)
            .build()

    @Provides
    fun provideLocationRequest(): LocationRequest = LocationRequest()

    @Provides
    fun provideGpsInteractor(googleApiClient: GoogleApiClient, locationRequest: LocationRequest) =
        GpsInteractor(googleApiClient, locationRequest)
}
