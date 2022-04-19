package br.com.githubsearch.presenter.model

import com.google.gson.annotations.SerializedName

data class Repo(
    @field:SerializedName("full_name")
    val fullName: String,
    @field:SerializedName("stargazers_count")
    val stars: Int,
    @field:SerializedName("forks_count")
    val forks: Int,
    val owner: Owner
) {

    data class Owner(
        val login: String,
        @field:SerializedName("avatar_url")
        val avatarUrl: String
    )
}
