package com.project.tiket_test.data.sources.remote

import com.project.tiket_test.data.sources.remote.model.UserSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

@JvmSuppressWildcards
interface ApiService {
    @GET("search/users?sort=login")
    suspend fun searchUsers(@Query("q") query: String,
                            @Query("page") page: Int,
                            @Query("per_page") perPage: Int): UserSearchResponse
}