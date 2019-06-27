package com.udmurtenergo.gpstracker.database.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.udmurtenergo.gpstracker.database.model.LogData
import java.util.Calendar

@Entity(tableName = "Logs")
data class EntityLog (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val description: String,
    val dateMillis: Long,
    val dateTimeZoneRawOfSet: Int) {

    @Ignore
    constructor(logData: LogData): this(
        logData.id,
        logData.title,
        logData.description,
        logData.date.time,
        Calendar.getInstance().timeZone.rawOffset
    )
}