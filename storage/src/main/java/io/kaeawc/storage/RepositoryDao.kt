package io.kaeawc.storage

import androidx.room.Dao
import androidx.room.Query
import arrow.core.Failure
import arrow.core.Try
import io.kaeawc.domain.Repository
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
abstract class RepositoryDao : BaseDao<Repository>() {

    @Query("SELECT * FROM repository")
    internal abstract fun queryAll(): Maybe<List<Repository>>

    fun getAll(): Single<Try<List<Repository>>> =
            queryAll().map { Try { it } }
                    .toSingle()
                    .onErrorReturn { Failure(it) }

    @Query("SELECT * FROM repository")
    internal abstract fun queryStreamOfAll(): Flowable<List<Repository>>

    fun getStreamOfAll(): Flowable<Try<List<Repository>>> =
            queryStreamOfAll().map { Try { it } }
                    .onErrorReturn { Failure(it) }

    @Query("SELECT * FROM repository WHERE name = :name LIMIT 1")
    internal abstract fun queryByName(name: String): Maybe<Repository>

    fun getByName(name: String): Single<Try<Repository>> =
            queryByName(name).map { Try { it } }
                    .toSingle()
                    .onErrorReturn { Failure(it) }

}
