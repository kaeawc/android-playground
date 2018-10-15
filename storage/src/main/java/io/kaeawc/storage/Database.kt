package io.kaeawc.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.kaeawc.domain.Repository

@Database(entities = [Repository::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {

    abstract fun repository(): RepositoryDao
}
