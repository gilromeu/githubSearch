package br.com.githubsearch.presenter.ui.repo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.githubsearch.databinding.ItemBinding
import br.com.githubsearch.presenter.model.Repo
import com.bumptech.glide.Glide

class RepoAdapter : PagingDataAdapter<Repo, RepoAdapter.RepoViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val itemView = ItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RepoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)

        with(holder) {
            binding.fullName.text = repo?.fullName
            binding.author.text = String.format("@${repo?.owner?.login}")
            binding.fork.text = repo?.forks.toString()
            binding.star.text = repo?.stars.toString()
            Glide.with(holder.itemView.context)
                .load(repo?.owner?.avatarUrl)
                .into(binding.imageOwner)
        }
    }

    inner class RepoViewHolder(val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem.fullName == newItem.fullName

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean =
                oldItem == newItem

        }
    }
}