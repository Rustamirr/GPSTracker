package com.udmurtenergo.gpstracker.repository

import com.udmurtenergo.gpstracker.database.model.FullLocation
import io.reactivex.Observable
import io.reactivex.Single

interface RepositoryLocation {
    fun insert(fullLocation: FullLocation)
    fun delete(fullLocation: FullLocation)

    fun getAll(): Observable<List<FullLocation>>
    fun getAllOnce(): Single<List<FullLocation>>
}
