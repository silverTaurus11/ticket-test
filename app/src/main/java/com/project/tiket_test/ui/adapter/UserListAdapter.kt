package com.project.tiket_test.ui.adapter

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.project.tiket_test.R
import com.project.tiket_test.data.sources.remote.model.UserItem
import com.project.tiket_test.data.utils.Resource
import com.project.tiket_test.databinding.GithubUserItemBinding
import com.project.tiket_test.databinding.NetworkStateItemBinding
import com.project.tiket_test.ui.adapter.view_holder.GithubUsersRecyclerViewHolder
import com.project.tiket_test.ui.adapter.view_holder.NetworkState
import com.project.tiket_test.ui.adapter.view_holder.NetworkStateViewHolder
import com.project.tiket_test.ui.errorCodeTranslate
import java.net.UnknownHostException

class UserListAdapter: PagedListAdapter<UserItem, RecyclerView.ViewHolder>(
    object : DiffUtil.ItemCallback<UserItem>(){
        override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
            return oldItem == newItem
        }

    }) {
    private var retryClickListener: View.OnClickListener?= null

    private var networkState: NetworkState?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            R.layout.github_user_item -> {
                val binding = GithubUserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                GithubUsersRecyclerViewHolder(binding)
            }
            R.layout.network_state_item -> {
                val binding = NetworkStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                NetworkStateViewHolder(binding)
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is GithubUsersRecyclerViewHolder -> holder.bind(getItem(position) as UserItem)
            is NetworkStateViewHolder -> holder.bind(networkState, retryClickListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isLoadMoreActive() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.github_user_item
        }
    }

    fun setNetworkState(resources: Resource<PagedList<UserItem>>, res: Resources){
        val previousState = this.networkState
        val hadExtraRow = isLoadMoreActive()
        when(resources){
            is Resource.LoadMoreLoading -> networkState = NetworkState.STATE_LOADING
            is Resource.Error -> {
                if(resources.isLoadMoreBehaviour){
                    networkState = NetworkState.STATE_ERROR.apply {
                        if(resources.throwable is UnknownHostException){
                            message = res.getString(R.string.error_no_internet_connection_message)
                        } else{
                            var message = resources.message?:res.getString(R.string.error_cant_get_information)
                            errorCodeTranslate(res, resources.throwable).apply {
                                if(isNotEmpty()){
                                    message = this
                                }
                            }
                            this.message = message
                        }
                    }
                    retryClickListener = resources.retry
                }
            }
            else -> networkState = NetworkState.STATE_SUCCESS
        }
        val hasExtraRow = isLoadMoreActive()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != this.networkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (isLoadMoreActive()) 1 else 0
    }

    private fun isLoadMoreActive(): Boolean{
        return networkState != null && networkState != NetworkState.STATE_SUCCESS
    }
}