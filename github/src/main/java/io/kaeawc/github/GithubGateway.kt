package io.kaeawc.github

import arrow.core.Failure
import arrow.core.Try
import io.kaeawc.github.models.Repository
import io.reactivex.Single

open class GithubGateway(open val githubApi: GithubApi) {

    fun getAll(): Single<Try<List<Repository>>> {
        return githubApi.getRepositories()
                .firstOrError()
                .map { Try { it } }
                .onErrorReturn { Failure(it) }
    }

    fun getRepositoryDetails(): Single<Try<Repository>> {
        return githubApi.getRepositoryDetails()
                .firstOrError()
                .map { Try { it } }
                .onErrorReturn { Failure(it) }
    }
}
