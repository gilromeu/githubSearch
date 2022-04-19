package br.com.githubsearch.data.api

import br.com.githubsearch.data.model.GithubResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("search/repositories?q=language:kotlin&sort=stars&order=desc")
    suspend fun getRepositories(@Query("page") page: Int): Response<GithubResponse>
}