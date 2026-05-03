package com.tebyl.aviariolocal.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BirdDao {
    @Query("SELECT * FROM birds ORDER BY dateMillis DESC")
    fun observeAll(): Flow<List<Bird>>

    @Query("SELECT * FROM birds WHERE id = :id")
    suspend fun get(id: Long): Bird?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(bird: Bird): Long

    @Query("UPDATE birds SET isFavorite = :fav WHERE id = :id")
    suspend fun setFavorite(id: Long, fav: Boolean)

    @Delete
    suspend fun delete(bird: Bird)

    @Query("SELECT COUNT(*) FROM birds")
    suspend fun count(): Int
}
