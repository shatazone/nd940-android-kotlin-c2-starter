package com.udacity.asteroidradar.api

import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type
import java.time.LocalDate

class LocalDateConverterFactory private constructor() :
    Converter.Factory() {
    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? {
        return if (type === LocalDate::class.java) {
            DateQueryConverter()
        } else null
    }

    private class DateQueryConverter :
        Converter<LocalDate?, String> {

        override fun convert(date: LocalDate?): String {
            return date.toString()
        }
    }

    companion object {
        fun create(): LocalDateConverterFactory {
            return LocalDateConverterFactory()
        }
    }
}