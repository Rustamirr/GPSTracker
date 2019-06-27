package com.udmurtenergo.gpstracker.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udmurtenergo.gpstracker.database.dao.LocationDao
import com.udmurtenergo.gpstracker.database.dao.LogDao
import com.udmurtenergo.gpstracker.database.entity.EntityLocation
import com.udmurtenergo.gpstracker.database.entity.EntityLog
import com.udmurtenergo.gpstracker.database.entity.EntitySatellite

@Database(entities = [EntityLocation::class, EntitySatellite::class, EntityLog::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getLocationDao(): LocationDao
    abstract fun getLogDao(): LogDao
}
