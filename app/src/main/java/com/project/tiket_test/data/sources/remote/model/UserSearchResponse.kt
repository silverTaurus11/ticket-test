package com.project.tiket_test.data.sources.remote.model

import com.google.gson.annotations.SerializedName

data class UserSearchResponse(
    @SerializedName("total_count") var totalCount: Int? = null,
    @SerializedName("incomplete_results") var incompleteResults: Boolean? = null,
    @SerializedName("items") var items: List<UserItem>? = null
)