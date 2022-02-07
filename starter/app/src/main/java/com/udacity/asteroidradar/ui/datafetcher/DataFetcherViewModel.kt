package com.udacity.asteroidradar.ui.datafetcher

import androidx.lifecycle.*
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

class DataFetcherViewModel(private val repository: AsteroidRepository) : ViewModel() {
    private val _showLoader = MutableLiveData<Boolean>()
    val showLoader: LiveData<Boolean> = _showLoader

    private val _showError = MutableLiveData<Boolean>()
    val showError: LiveData<Boolean> = _showError

    private val _navigateToMainScreen = MutableLiveData<Boolean>()
    val navigateToMainScreen: LiveData<Boolean> = _navigateToMainScreen

    init {
        viewModelScope.launch {
            if (!repository.isLoaded()) {
                Timber.d("Refreshing")
                refreshAppData()
            } else {
                Timber.d("No Need To Refresh")
                _navigateToMainScreen.value = true
            }
        }
    }

    fun refreshAppData() {
        viewModelScope.launch {
            try {
                _showLoader.postValue(true)
                _showError.postValue(false)

                repository.refreshPictureOfTheDay()
                repository.fetchUpcomingAsteroids()

                _showLoader.postValue(false)
                _navigateToMainScreen.postValue(true)
            } catch (e: Exception) {
                Timber.e("Error has occurred while fetching new data...", e)
                _showError.postValue(true)
                _showLoader.postValue(false)
            }
        }
    }

    class Factory(private val repository: AsteroidRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DataFetcherViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DataFetcherViewModel(repository) as T
            }

            throw IllegalArgumentException("Unable to construct viewmodel")
        }

    }
}