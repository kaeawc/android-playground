package io.kaeawc.github

import io.kaeawc.github.models.Repository
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    companion object {
        const val USERNAME = "kaeawc"
    }

    @GET("/users/$USERNAME/starred")
    fun getStarredRepositories(@Query("sort") sort: String, @Query("direction") direction: String): Observable<List<Repository>>

    @GET("/users/$USERNAME/repos")
    fun getRepositories(@Query("type") type: String, @Query("sort") sort: String, @Query("direction") direction: String): Observable<List<Repository>>

    @GET("/repos/$USERNAME/:name")
    fun getRepositoryDetails(@Query("name") name: String): Observable<Repository>

}
