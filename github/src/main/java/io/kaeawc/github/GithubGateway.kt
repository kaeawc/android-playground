package io.kaeawc.github

import arrow.core.Failure
import arrow.core.Try
import io.kaeawc.github.models.Repository
import io.reactivex.Single

open class GithubGateway(open val githubApi: GithubApi) {

    fun getAll(): Single<Try<List<Repository>>> {
        return githubApi.getRepositories("public", "updated", "desc")
                .firstOrError()
                .map { Try { it } }
                .onErrorReturn { Failure(it) }
    }

    fun getRepository(name: String): Single<Try<Repository>> {
        return githubApi.getRepositoryDetails(name)
                .firstOrError()
                .map { Try { it } }
                .onErrorReturn { Failure(it) }
    }
}
