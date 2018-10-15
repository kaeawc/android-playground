package io.kaeawc.storage

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
abstract class BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upsert(data: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun upsertMany(vararg data: T)

    @Delete
    abstract fun delete(data: T)

    @Delete
    abstract fun deleteMany(vararg data: T)

}
