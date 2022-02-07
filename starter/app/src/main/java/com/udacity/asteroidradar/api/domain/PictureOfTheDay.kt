package com.udacity.asteroidradar.api.domain

import com.squareup.moshi.Json
import java.time.LocalDate

data class PictureOfTheDay(
    @Json(name = "date") var date: LocalDate,
    @Json(name = "copyright") var copyright: String? = null,
    @Json(name = "explanation") var explanation: String? = null,
    @Json(name = "hdurl") var hdurl: String? = null,
    @Json(name = "media_type") var mediaType: String? = null,
    @Json(name = "service_version") var serviceVersion: String? = null,
    @Json(name = "title") var title: String? = null,
    @Json(name = "url") var url: String? = null
)