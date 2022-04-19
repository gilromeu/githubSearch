package br.com.githubsearch.presenter.ui.repo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import br.com.githubsearch.data.api.GithubApi
import br.com.githubsearch.data.api.ServiceApi
import br.com.githubsearch.databinding.RepoGithubFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RepoGithubFragment : Fragment() {

    private lateinit var viewModel: RepoGithubViewModel
    private lateinit var binding: RepoGithubFragmentBinding
    private lateinit var adapter: RepoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModelFactory = RepoGithubViewModel.Factory(
            ServiceApi().createService(GithubApi::class.java)
        )

        adapter = RepoAdapter()
        viewModel = ViewModelProvider(this, viewModelFactory)[RepoGithubViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RepoGithubFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listItems.adapter = adapter

        lifecycleScope.launch {
            viewModel.repo.collectLatest { pagingData ->
                binding.progressBar.hide()
                binding.listItems.show()
                adapter.submitData(pagingData)
            }
        }
    }

    private fun View.hide() {
        this.visibility = View.GONE
    }

    private fun View.show() {
        this.visibility = View.VISIBLE
    }
}