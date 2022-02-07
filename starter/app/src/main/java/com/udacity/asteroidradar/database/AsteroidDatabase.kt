package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udacity.asteroidradar.database.dao.AsteroidDao
import com.udacity.asteroidradar.database.dao.PictureOfTheDayDao
import com.udacity.asteroidradar.database.entities.AsteroidEntity
import com.udacity.asteroidradar.database.entities.PictureOfTheDayEntity
import com.udacity.asteroidradar.database.util.LocalDateConverter

@Database(entities = [PictureOfTheDayEntity::class, AsteroidEntity::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateConverter::class)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val pictureOfTheDayDao: PictureOfTheDayDao
    abstract val asteroidDao: AsteroidDao

    companion object {
        @Volatile
        private var INSTANCE: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(context.applicationContext, AsteroidDatabase::class.java, "asteroidDB")
                            .fallbackToDestructiveMigration()
                            .build()
                }
                return INSTANCE!!
            }
        }
    }
}