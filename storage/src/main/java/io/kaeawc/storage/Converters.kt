package io.kaeawc.storage

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromListOfStrings(value: List<String>?): String? {
        return value?.joinToString("|")
    }

    @TypeConverter
    fun toListOfStrings(value: String?): List<String>? {
        return value?.split("|")
    }
}
