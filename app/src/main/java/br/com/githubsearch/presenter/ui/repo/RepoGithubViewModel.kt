package br.com.githubsearch.presenter.ui.repo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import br.com.githubsearch.data.api.GithubApi
import br.com.githubsearch.domain.GetRepoPagingSource
import br.com.githubsearch.presenter.model.Repo
import kotlinx.coroutines.flow.Flow

class RepoGithubViewModel(
    private val service: GithubApi
) : ViewModel() {

    val repo: Flow<PagingData<Repo>> = Pager(
        config = PagingConfig(pageSize = 50, enablePlaceholders = false),
        pagingSourceFactory = { GetRepoPagingSource(service) }
    ).flow.cachedIn(viewModelScope)

    class Factory(
        private val service: GithubApi
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RepoGithubViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RepoGithubViewModel(service) as T
            }
            throw IllegalArgumentException("Classe ViewModel inválida")
        }
    }
}