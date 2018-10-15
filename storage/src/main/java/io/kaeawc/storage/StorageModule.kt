package io.kaeawc.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import io.kaeawc.domain.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class StorageModule(private val context: Context) {

    @Provides
    @ApplicationScope
    fun providesDatabase(): Database =
            Room.databaseBuilder(context, Database::class.java, "playground.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

    @Provides
    @ApplicationScope
    fun providesSharedPreferences(): SharedPreferences =
            context.getSharedPreferences("playground", Context.MODE_PRIVATE)
}
