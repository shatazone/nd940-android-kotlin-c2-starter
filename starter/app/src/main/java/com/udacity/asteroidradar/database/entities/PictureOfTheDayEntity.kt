package com.udacity.asteroidradar.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.util.*

@Entity(tableName = PictureOfTheDayEntity.TABLE_NAME)
data class PictureOfTheDayEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_DATE)
    var date: LocalDate,

    @ColumnInfo(name = COLUMN_TITLE)
    var title: String?,

    @ColumnInfo(name = COLUMN_IMAGE_URL)
    var imageUrl: String?,

    @ColumnInfo(name = COLUMN_MEDIA_TYPE)
    var mediaType: String?
) {
    companion object {
        const val TABLE_NAME = "pic_of_the_day"
        const val COLUMN_TITLE = "title"
        const val COLUMN_IMAGE_URL = "image_url"
        const val COLUMN_DATE = "date"
        const val COLUMN_MEDIA_TYPE = "media_type"
    }
}