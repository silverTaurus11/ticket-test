package com.project.tiket_test.data.sources.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.project.tiket_test.data.sources.remote.model.UserSearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource constructor(private val apiService: ApiService) {

    companion object{
        const val PER_PAGE_SIZE = 20
    }
    fun searchUser(query: String, page: Int = 1, perPage: Int = PER_PAGE_SIZE): LiveData<ApiResponse<UserSearchResponse>>{
        return flow {
            try {
                val response = apiService.searchUsers(query, page, perPage)
                val coinList = response.items
                if (coinList.isNullOrEmpty()){
                    emit(ApiResponse.Empty)
                } else {
                    emit(ApiResponse.Success(response))
                }
            } catch (e : Exception){
                emit(ApiResponse.Error(e))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO).asLiveData()
    }
}