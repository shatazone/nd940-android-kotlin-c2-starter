package com.udacity.asteroidradar.ui.main

import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.udacity.asteroidradar.database.entities.AsteroidEntity
import com.udacity.asteroidradar.database.entities.PictureOfTheDayEntity
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.IllegalArgumentException
import java.time.LocalDate
import java.util.*

class MainViewModel(private val repository: AsteroidRepository) : ViewModel() {
    private val _filter = MediatorLiveData<Filter>().apply { value = Filter.THIS_WEEK_ONLY }
    val asteroids: LiveData<List<AsteroidEntity>> =
        Transformations.switchMap(_filter) { filter ->
            val allAsteroids = repository.asteroids
            val startDate = LocalDate.now()
            val endDate = startDate.plusDays(7)

            when (filter) {
                Filter.NONE -> repository.asteroids

                Filter.THIS_WEEK_ONLY -> Transformations.switchMap(allAsteroids) { entity ->
                    val toList = entity.filter {
                        !it.closeApproachDate!!.isBefore(startDate) && !it.closeApproachDate!!.isAfter(
                            endDate
                        )
                    }.toList()
                    MutableLiveData(toList)
                }

                Filter.TODAY_ONLY -> Transformations.switchMap(allAsteroids) { entity ->
                    val toList = entity.filter {
                        it.closeApproachDate!! == startDate
                    }.toList()
                    MutableLiveData(toList)
                }

                else -> throw IllegalArgumentException("Unknown filter '$filter'")
            }
        }

    val pictureOfTheDay: LiveData<PictureOfTheDayEntity> = repository.pictureOfTheDay


    private val _displayAsteroidDetails = MutableLiveData<AsteroidEntity?>()
    val displayAsteroidDetails: LiveData<AsteroidEntity?> = _displayAsteroidDetails

    private val _fetchingData = MutableLiveData<Boolean>()
    val fetchingData: LiveData<Boolean> = _fetchingData

    private val _displayConnectionError = MutableLiveData<Boolean>()
    val displayConnectionError: LiveData<Boolean> = _displayConnectionError

    fun showWeekAsteroids() {
        _filter.value = Filter.THIS_WEEK_ONLY
    }

    fun showTodayAsteroids() {
        _filter.value = Filter.TODAY_ONLY
    }

    fun showAllAsteroids() {
        _filter.value = Filter.NONE
    }

    fun refreshData() {
        viewModelScope.launch {
            try {
                _displayConnectionError.value = (false)
                _fetchingData.value = (true)
                Timber.d("MainViewModel", "------ Refreshing")
                repository.fetchUpcomingAsteroids()
                repository.refreshPictureOfTheDay()
                Timber.d("MainViewModel", "------ Done")

                _fetchingData.value = (false)
            } catch (e: Exception) {
                Timber.e("MainViewModel", "Error while fetching data", e)
                _fetchingData.value = (false)
                _displayConnectionError.value = (true)
            }
        }
    }

    fun displayAsteroidDetails(asteroid: AsteroidEntity) {
        _displayAsteroidDetails.value = asteroid
    }

    fun onAsteroidDetailsDisplayedCompleted() {
        _displayAsteroidDetails.value = null
    }

    fun onDisplayConnectionErrorCompleted() {
        _displayConnectionError.value = false
    }

    enum class Filter {
        NONE, TODAY_ONLY, THIS_WEEK_ONLY
    }
}