package com.udmurtenergo.gpstracker.repository.implementation

import com.udmurtenergo.gpstracker.database.dao.LogDao
import com.udmurtenergo.gpstracker.database.model.LogData
import com.udmurtenergo.gpstracker.repository.RepositoryLog
import io.reactivex.Observable

class RepositoryLogImpl(
    private val logDao: LogDao) : RepositoryLog {

    override fun insert(logData: LogData) = logDao.insert(logData)
    override fun getAll(): Observable<List<LogData>> = logDao.getAll()
}
