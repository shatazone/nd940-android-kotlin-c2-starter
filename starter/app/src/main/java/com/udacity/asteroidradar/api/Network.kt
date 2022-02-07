package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.domain.PictureOfTheDay
import com.udacity.asteroidradar.api.domain.FeedResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate
import java.util.*

private val interceptor: HttpLoggingInterceptor =  HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
private val okHttpClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .add(LocalDate::class.java, LocalDateJsonAdapter())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .addConverterFactory(LocalDateConverterFactory.create())
    .client(okHttpClient)
    .baseUrl(Constants.BASE_URL)
    .build()


interface NasaApiService {
    @GET("neo/rest/v1/feed")
    suspend fun getFeed(
        @Query("start_date") startDate: LocalDate? = null,
        @Query("end_date") endDate: LocalDate? = null,
        @Query("api_key") apiKey: String? = Constants.API_KEY
    ): FeedResponse

    @GET("/planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String? = Constants.API_KEY): PictureOfTheDay
}

object Network {
    val NASA_API: NasaApiService = retrofit.create(NasaApiService::class.java)
}
