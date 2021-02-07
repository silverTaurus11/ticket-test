package com.project.tiket_test.ui.adapter.view_holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.project.tiket_test.databinding.NetworkStateItemBinding
import com.project.tiket_test.ui.toVisible

enum class NetworkState(var message: String?= null){
    STATE_LOADING,
    STATE_SUCCESS,
    STATE_ERROR
}

class NetworkStateViewHolder(private val binding: NetworkStateItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(state: NetworkState?, retryClickListener: View.OnClickListener?){
        binding.progressBar.toVisible(state == NetworkState.STATE_LOADING)
        binding.retryButton.apply {
            toVisible(state == NetworkState.STATE_ERROR)
            setOnClickListener(retryClickListener)
        }
        binding.errorMsg.toVisible(state == NetworkState.STATE_ERROR)
        binding.errorMsg.text = state?.message
    }
}