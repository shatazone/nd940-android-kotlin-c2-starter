package com.udacity.asteroidradar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.database.entities.AsteroidEntity

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM ${AsteroidEntity.TABLE_NAME} ORDER BY ${AsteroidEntity.COLUMN_CLOSE_APPROACH_DATE} ASC")
    fun getAsteroids(): LiveData<List<AsteroidEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAsteroid(vararg asteroid: AsteroidEntity)

    @Query("SELECT COUNT(${AsteroidEntity.COLUMN_ID}) FROM ${AsteroidEntity.TABLE_NAME}")
    fun getRowCount(): Int
}