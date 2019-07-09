package com.udmurtenergo.gpstracker.repository

import com.udmurtenergo.gpstracker.database.model.LogData
import io.reactivex.Observable

interface RepositoryLog {
    fun insert(logData: LogData)
    fun getAll(): Observable<List<LogData>>
}
