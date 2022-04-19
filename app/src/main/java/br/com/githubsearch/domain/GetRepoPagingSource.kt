package br.com.githubsearch.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import br.com.githubsearch.data.api.ApiResponse
import br.com.githubsearch.data.api.GithubApi
import br.com.githubsearch.data.api.parseResponse
import br.com.githubsearch.presenter.model.Repo

class GetRepoPagingSource(
    private val service: GithubApi
) : PagingSource<Int, Repo>() {

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)
                ?.prevKey?.plus(1) ?: state.closestPageToPosition(position)
                ?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        return try {
            val page = params.key ?: 1
            val repoList = getRepo(page)
            val nextKey = if (repoList.isEmpty()) null else page + (params.loadSize/30)

            LoadResult.Page(
                data = repoList,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private suspend fun getRepo(page: Int): List<Repo> {
        return when (val result = service.getRepositories(page).parseResponse()) {
            is ApiResponse.Success -> {
                result.value.items
            }
            is ApiResponse.Failure -> listOf()
        }
    }
}