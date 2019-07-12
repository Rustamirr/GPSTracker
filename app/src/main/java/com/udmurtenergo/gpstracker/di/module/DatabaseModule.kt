package com.udmurtenergo.gpstracker.di.module

import android.content.Context
import androidx.room.Room
import com.udmurtenergo.gpstracker.database.AppDatabase
import com.udmurtenergo.gpstracker.repository.RepositoryLocation
import com.udmurtenergo.gpstracker.repository.RepositoryLog
import com.udmurtenergo.gpstracker.repository.RepositoryLocationImpl
import com.udmurtenergo.gpstracker.repository.RepositoryLogImpl
import dagger.Module
import dagger.Provides

import javax.inject.Singleton

@Module
class DatabaseModule(private val databaseName: String) {
    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
            //.allowMainThreadQueries()
            .build()

    @Singleton
    @Provides
    fun provideRepositoryLocation(database: AppDatabase): RepositoryLocation =
        RepositoryLocationImpl(database.getLocationDao())

    @Singleton
    @Provides
    fun provideRepositoryLog(database: AppDatabase): RepositoryLog =
        RepositoryLogImpl(database.getLogDao())
}
