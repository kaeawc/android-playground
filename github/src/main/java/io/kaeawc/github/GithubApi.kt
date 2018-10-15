package io.kaeawc.github

import io.kaeawc.github.models.Repository
import io.reactivex.Observable
import retrofit2.http.GET

interface GithubApi {

    @GET("/repositories")
    fun getRepositories(): Observable<List<Repository>>

    @GET("/repository/details")
    fun getRepositoryDetails(): Observable<Repository>

}
