package com.udacity.asteroidradar.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.database.entities.PictureOfTheDayEntity

@Dao
interface PictureOfTheDayDao {
    @Query("SELECT * FROM ${PictureOfTheDayEntity.TABLE_NAME} ORDER BY ${PictureOfTheDayEntity.COLUMN_DATE} DESC")
    fun getAllPictures(): LiveData<List<PictureOfTheDayEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPicture(picOfTheDay: PictureOfTheDayEntity)

    @Query("SELECT COUNT(${PictureOfTheDayEntity.COLUMN_DATE}) FROM ${PictureOfTheDayEntity.TABLE_NAME}")
    fun getRowCount(): Int
}