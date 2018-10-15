package io.kaeawc.storage

import androidx.room.TypeConverter
import org.threeten.bp.Instant

class Converters {

    @TypeConverter
    fun fromInstant(value: Instant?): Long? {
        return value?.toEpochMilli()
    }

    @TypeConverter
    fun toInstant(value: Long?): Instant? {
        return if (value == null) null else Instant.ofEpochMilli(value)
    }

    @TypeConverter
    fun fromListOfStrings(value: List<String>?): String? {
        return value?.joinToString("|")
    }

    @TypeConverter
    fun toListOfStrings(value: String?): List<String>? {
        return value?.split("|")
    }
}
