package io.kaeawc.repos

import androidx.recyclerview.widget.DiffUtil
import arrow.core.Failure
import arrow.core.Success
import arrow.core.Try
import arrow.core.orNull
import io.kaeawc.domain.ApplicationScope
import io.kaeawc.domain.Repository
import io.kaeawc.domain.moreThanHoursAgo
import io.kaeawc.github.GithubGateway
import io.kaeawc.storage.Database
import io.kaeawc.storage.Prefs
import io.reactivex.Flowable
import io.reactivex.Single
import org.threeten.bp.Instant
import java.lang.IllegalStateException
import java.lang.NullPointerException
import javax.inject.Inject

@ApplicationScope
open class GithubRepository @Inject constructor(
        open val gateway: GithubGateway,
        open val prefs: Prefs,
        open val database: Database
) {

    /**
     * Get all [Repository] records, whether from the network or local cache.
     */
    fun getAll(): Single<Try<List<Repository>>> {
        if (prefs.lastRepositoryFetch.moreThanHoursAgo(2)) {
            return getAllRemote()
        }
        return getAllLocal(requestMoreIfEmpty = true)
    }

    fun getStreamOfAll(): Flowable<Try<List<Repository>>> {
        return getLocalStreamOfAll()
    }

    /**
     * Get stream of [Repository] records and calculate the [DiffResult] between the current
     * and previous result.
     */
    fun getStreamingDiffResultOfAll(): Flowable<Try<Pair<List<Repository>, DiffUtil.DiffResult>>> {
        return getLocalStreamOfAll()
                .map { Pair<Try<List<Repository>>, Try<DiffUtil.DiffResult>>(it, Failure(NullPointerException("No diff result"))) }
                .scan {
                    oldPair, newPair ->
                    val old = oldPair.first
                    val new = newPair.first
                    val result = Try {

                        val callback = if (old is Success && new is Success) {
                            ListDiffCallback(old.value, new.value)
                        } else if (new is Success) {
                            ListDiffCallback(emptyList(), new.value)
                        } else if (old is Success) {
                            ListDiffCallback(old.value, old.value)
                        } else {
                            throw IllegalStateException("Could not process update to stream")
                        }

                        DiffUtil.calculateDiff(callback)
                    }

                    new to result
                }.map {
                    result ->
                    val (items, diffResult) = result
                    if (items is Success && diffResult is Success) {
                        Success(items.value to diffResult.value)
                    } else if (items is Failure) {
                        items
                    } else if (diffResult is Failure) {
                        diffResult
                    } else {
                        Failure(IllegalStateException("Could not process update to stream"))
                    }
                }
    }

    /**
     * Always attempt to use local data before going to the network
     */
    fun getRepositoryByName(name: String): Single<Try<Repository>> {
        return database.repository().getByName(name)
    }

    private fun getAllRemote(): Single<Try<List<Repository>>> {
        return gateway.getAll().map {
            result ->
            prefs.lastRepositoryFetch = Instant.now()
            result.map { success -> success.mapNotNull(this::toLocal) }
        }
    }

    private fun getAllLocal(requestMoreIfEmpty: Boolean): Single<Try<List<Repository>>> {
        return database.repository().getAll().flatMap {
            result ->
            val records = result.orNull() ?: emptyList()

            if (records.isEmpty() && requestMoreIfEmpty) {
                getAllRemote()
            } else {
                Single.just(Success(records))
            }
        }
    }

    /**
     * Get stream of [Repository] records from local storage. Emissions are
     * to be expected for any changes.
     */
    private fun getLocalStreamOfAll(): Flowable<Try<List<Repository>>> {
        return database.repository().getStreamOfAll()
    }

    private fun toLocal(response: io.kaeawc.github.models.Repository): Repository? {
        return Repository(
                response.name ?: return null,
                response.created ?: return null)
    }
}
