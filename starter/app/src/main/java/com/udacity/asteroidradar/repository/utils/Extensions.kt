package com.udacity.asteroidradar.repository.utils

import com.udacity.asteroidradar.api.domain.Asteroid
import com.udacity.asteroidradar.api.domain.PictureOfTheDay
import com.udacity.asteroidradar.database.entities.AsteroidEntity
import com.udacity.asteroidradar.database.entities.PictureOfTheDayEntity

fun Asteroid.toEntity(): AsteroidEntity {
    return AsteroidEntity(
        id = id,
        codename = name,
        closeApproachDate = closeApproachData[0].closeApproachDate,
        absoluteMagnitude = absoluteMagnitudeH,
        estimatedDiameter = estimatedDiameter?.kilometers?.estimatedDiameterMax,
        relativeVelocity = closeApproachData[0].relativeVelocity?.kilometersPerSecond?.toDouble(),
        distanceFromEarth = closeApproachData[0].missDistance?.kilometers?.toDouble(),
        hazardous = isPotentiallyHazardousAsteroid
    )
}

fun PictureOfTheDay.toEntity(): PictureOfTheDayEntity {
    return PictureOfTheDayEntity(
        title = title,
        imageUrl = url,
        date = date,
        mediaType = mediaType
    )
}