package com.udacity.asteroidradar.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Entity(tableName = AsteroidEntity.TABLE_NAME)
@Parcelize
data class AsteroidEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    var id: String,

    @ColumnInfo(name = COLUMN_CODENAME)
    var codename: String?,

    @ColumnInfo(name = COLUMN_CLOSE_APPROACH_DATE)
    var closeApproachDate: LocalDate?,

    @ColumnInfo(name = COLUMN_ABSOLUTE_MAGNITUDE)
    var absoluteMagnitude: Double?,

    @ColumnInfo(name = COLUMN_ESTIMATED_DIAMETER)
    var estimatedDiameter: Double?,

    @ColumnInfo(name = COLUMN_RELATIVE_VELOCITY)
    var relativeVelocity: Double?,

    @ColumnInfo(name = COLUMN_DISTANCE_FROM_EARTH)
    var distanceFromEarth: Double?,

    @ColumnInfo(name = COLUMN_HAZARDOUS)
    var hazardous: Boolean?

) : Parcelable {
    companion object {
        const val TABLE_NAME = "asteroids"
        const val COLUMN_ID = "id"
        const val COLUMN_CODENAME = "codename"
        const val COLUMN_CLOSE_APPROACH_DATE = "close_approach_date"
        const val COLUMN_ABSOLUTE_MAGNITUDE = "absolute_magnitude"
        const val COLUMN_ESTIMATED_DIAMETER = "estimated_diameter"
        const val COLUMN_RELATIVE_VELOCITY = "relative_velocity"
        const val COLUMN_DISTANCE_FROM_EARTH = "distance_from_earth"
        const val COLUMN_HAZARDOUS = "hazardous"
    }
}