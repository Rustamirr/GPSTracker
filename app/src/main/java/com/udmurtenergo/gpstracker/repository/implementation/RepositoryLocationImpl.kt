package com.udmurtenergo.gpstracker.repository.implementation

import com.udmurtenergo.gpstracker.database.dao.LocationDao
import com.udmurtenergo.gpstracker.database.model.FullLocation
import com.udmurtenergo.gpstracker.repository.RepositoryLocation
import io.reactivex.Observable
import io.reactivex.Single

class RepositoryLocationImpl(
    private val locationDao: LocationDao) : RepositoryLocation {

    override fun insert(fullLocation: FullLocation) = locationDao.insert(fullLocation)
    override fun delete(fullLocation: FullLocation) = locationDao.delete(fullLocation)

    override fun getAll(): Observable<List<FullLocation>> = locationDao.getAll()
    override fun getAllOnce(): Single<List<FullLocation>> = locationDao.getAllOnce()
}
