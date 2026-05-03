package com.tebyl.aviariolocal.data

import kotlinx.coroutines.flow.Flow

class BirdRepository(private val dao: BirdDao) {
    fun observeAll(): Flow<List<Bird>> = dao.observeAll()
    suspend fun get(id: Long): Bird? = dao.get(id)
    suspend fun upsert(bird: Bird): Long = dao.upsert(bird)
    suspend fun setFavorite(id: Long, fav: Boolean) = dao.setFavorite(id, fav)
    suspend fun delete(bird: Bird) = dao.delete(bird)
}
