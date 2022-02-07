package com.udacity.asteroidradar.api.domain

import com.squareup.moshi.Json
import java.time.LocalDate

data class FeedResponse(
    @Json(name = "links") var links: Links? = null,
    @Json(name = "element_count") var elementCount: Int? = null,
    @Json(name = "near_earth_objects") var nearEarthObjects: Map<LocalDate, List<Asteroid>> = emptyMap()
)

data class Links(
    @Json(name = "next") var next: String? = null,
    @Json(name = "prev") var prev: String? = null,
    @Json(name = "self") var self: String? = null
)

data class Kilometers(
    @Json(name = "estimated_diameter_min") var estimatedDiameterMin: Double? = null,
    @Json(name = "estimated_diameter_max") var estimatedDiameterMax: Double? = null
)

data class Meters(
    @Json(name = "estimated_diameter_min") var estimatedDiameterMin: Double? = null,
    @Json(name = "estimated_diameter_max") var estimatedDiameterMax: Double? = null
)

data class Miles(
    @Json(name = "estimated_diameter_min") var estimatedDiameterMin: Double? = null,
    @Json(name = "estimated_diameter_max") var estimatedDiameterMax: Double? = null
)

data class Feet(
    @Json(name = "estimated_diameter_min") var estimatedDiameterMin: Double? = null,
    @Json(name = "estimated_diameter_max") var estimatedDiameterMax: Double? = null
)

data class EstimatedDiameter(
    @Json(name = "kilometers") var kilometers: Kilometers? = null,
    @Json(name = "meters") var meters: Meters? = null,
    @Json(name = "miles") var miles: Miles? = null,
    @Json(name = "feet") var feet: Feet? = null
)

data class RelativeVelocity(
    @Json(name = "kilometers_per_second") var kilometersPerSecond: String? = null,
    @Json(name = "kilometers_per_hour") var kilometersPerHour: String? = null,
    @Json(name = "miles_per_hour") var milesPerHour: String? = null
)

data class MissDistance(
    @Json(name = "astronomical") var astronomical: String? = null,
    @Json(name = "lunar") var lunar: String? = null,
    @Json(name = "kilometers") var kilometers: String? = null,
    @Json(name = "miles") var miles: String? = null
)

data class CloseApproachData(
    @Json(name = "close_approach_date") var closeApproachDate: LocalDate? = null,
    @Json(name = "close_approach_date_full") var closeApproachDateFull: String? = null,
    @Json(name = "epoch_date_close_approach") var epochDateCloseApproach: Long? = null,
    @Json(name = "relative_velocity") var relativeVelocity: RelativeVelocity? = null,
    @Json(name = "miss_distance") var missDistance: MissDistance? = null,
    @Json(name = "orbiting_body") var orbitingBody: String? = null
)

data class Asteroid(
    @Json(name = "links") var links: Links? = null,
    @Json(name = "id") var id: String,
    @Json(name = "neo_reference_id") var neoReferenceId: String? = null,
    @Json(name = "name") var name: String? = null,
    @Json(name = "nasa_jpl_url") var nasaJplUrl: String? = null,
    @Json(name = "absolute_magnitude_h") var absoluteMagnitudeH: Double? = null,
    @Json(name = "estimated_diameter") var estimatedDiameter: EstimatedDiameter? = null,
    @Json(name = "is_potentially_hazardous_asteroid") var isPotentiallyHazardousAsteroid: Boolean? = null,
    @Json(name = "close_approach_data") var closeApproachData: List<CloseApproachData> = arrayListOf(),
    @Json(name = "is_sentry_object") var isSentryObject: Boolean? = null
)