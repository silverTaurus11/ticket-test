package com.project.tiket_test.ui.adapter.view_holder

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.project.tiket_test.data.sources.remote.model.UserItem
import com.project.tiket_test.databinding.GithubUserItemBinding


class GithubUsersRecyclerViewHolder(private val binding: GithubUserItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: UserItem){
        Glide.with(itemView).load(item.avatarUrl).diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop().into(binding.userImage)
        binding.userName.text = item.login
        itemView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.data = Uri.parse(item.htmlUrl)
            it.context.startActivity(intent)
        }
    }
}