package com.udmurtenergo.gpstracker.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.udmurtenergo.gpstracker.database.entity.EntityLog
import com.udmurtenergo.gpstracker.database.model.LogData
import io.reactivex.Observable
import java.util.Date

@Dao
abstract class LogDao {

    fun insert(logData: LogData) {
        insertEntityLog(EntityLog(logData))
    }

    fun getAll(): Observable<List<LogData>>{
        return getEntityLogsDesc().map (this::entityLogToLogData)
    }

    private fun entityLogToLogData(entityLogs: List<EntityLog>) =
        entityLogs.map { LogData(it.id, it.title, it.description, Date(it.dateMillis)) }

    @Insert
    abstract fun insertEntityLog(entityLog: EntityLog)

    @Query("SELECT * FROM Logs ORDER BY Logs.dateMillis DESC")
    abstract fun getEntityLogsDesc(): Observable<List<EntityLog>>
}
