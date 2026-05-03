package com.tebyl.aviariolocal.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Entity(tableName = "birds")
@TypeConverters(StringListConverter::class)
data class Bird(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val species: String?,
    val sci: String?,
    val photoUri: String,
    val dateMillis: Long,
    val dateStr: String,
    val location: String,
    val locShort: String,
    val behavior: String,
    val notes: String,
    val tags: List<String>,
    val isFavorite: Boolean = false,
    val lat: Double? = null,
    val lng: Double? = null
)

class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> =
        if (value.isEmpty()) emptyList() else value.split(",").map { it.trim() }

    @TypeConverter
    fun fromList(list: List<String>): String = list.joinToString(",")
}
