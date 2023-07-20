package fr.florianmartin.databasetests.data.repositories

import kotlinx.coroutines.flow.Flow

interface AppRepository<T> {

    fun fetchAllFromLocal(): Flow<List<T>>

    fun fetchFromLocal(id: Int): Flow<T>

    suspend fun insertAllIntoDb(list: List<T>)

    suspend fun insertIntoDb(item: T)
}
