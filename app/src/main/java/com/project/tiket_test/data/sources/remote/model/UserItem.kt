package com.project.tiket_test.data.sources.remote.model

import com.google.gson.annotations.SerializedName

data class UserItem(
    @SerializedName("login") val login: String? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("node_id") val nodeId: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("gravatar_id") val gravatarId: String? = null,
    @SerializedName("html_url") val htmlUrl: String? = null,
    @SerializedName("followers_url") val followersUrl: String? = null,
    @SerializedName("subscriptions_url") val subscriptionsUrl: String? = null,
    @SerializedName("organizations_url") val organizationsUrl: String? = null,
    @SerializedName("repos_url") val reposUrl: String? = null,
    @SerializedName("received_events_url") val receivedEventsUrl: String? = null,
    @SerializedName("type") val type: String? = null,
    @SerializedName("score") val score: Float? = null
)