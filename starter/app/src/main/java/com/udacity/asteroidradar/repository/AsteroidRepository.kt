package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.NasaApiService
import com.udacity.asteroidradar.api.domain.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.entities.AsteroidEntity
import com.udacity.asteroidradar.database.entities.PictureOfTheDayEntity
import com.udacity.asteroidradar.repository.utils.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate

class AsteroidRepository(
    private val asteroidDatabase: AsteroidDatabase,
    private val nasaApi: NasaApiService
) {
    private val _allPics: LiveData<List<PictureOfTheDayEntity>> =
        asteroidDatabase.pictureOfTheDayDao.getAllPictures()

    private val _asteroids: LiveData<List<AsteroidEntity>> =
        asteroidDatabase.asteroidDao.getAsteroids()
    val asteroids = _asteroids

    val pictureOfTheDay: LiveData<PictureOfTheDayEntity> = Transformations.map(_allPics) {
        it[0]
    }

    suspend fun fetchUpcomingAsteroids(startDate: LocalDate? = null, endDate: LocalDate? = null) {
        withContext(Dispatchers.IO) {
            val asteroidList = nasaApi.getFeed(startDate = startDate, endDate = endDate)
                .nearEarthObjects
                .entries
                .flatMap { it.value.map(Asteroid::toEntity) }
                .toTypedArray()

            asteroidDatabase.asteroidDao.addAsteroid(*asteroidList)
        }
    }

    suspend fun refreshPictureOfTheDay() {
        withContext(Dispatchers.IO) {
            val pictureOfTheDayEntity = nasaApi.getPictureOfTheDay().toEntity()
            asteroidDatabase.pictureOfTheDayDao.addPicture(pictureOfTheDayEntity)
        }
    }

    suspend fun isLoaded(): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext asteroidDatabase.asteroidDao.getRowCount() > 0
        }
    }
}