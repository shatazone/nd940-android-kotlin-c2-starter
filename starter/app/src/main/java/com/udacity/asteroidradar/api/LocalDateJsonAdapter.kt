package com.udacity.asteroidradar.api

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.io.IOException
import java.time.LocalDate
import kotlin.jvm.Synchronized
import kotlin.Throws

class LocalDateJsonAdapter : JsonAdapter<LocalDate>() {
    @Synchronized
    @Throws(IOException::class)
    override fun fromJson(reader: JsonReader): LocalDate {
        val string = reader.nextString()
        return LocalDate.parse(string)
    }

    @Synchronized
    @Throws(IOException::class)
    override fun toJson(writer: JsonWriter, value: LocalDate?) {
        val string = value.toString()
        writer.value(string)
    }
}