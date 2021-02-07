package com.project.tiket_test.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.tiket_test.data.sources.remote.ApiResponse
import com.project.tiket_test.data.sources.remote.RemoteDataSource
import com.project.tiket_test.data.sources.remote.RemoteDataSource.Companion.PER_PAGE_SIZE
import com.project.tiket_test.data.sources.remote.model.UserItem
import com.project.tiket_test.data.sources.remote.model.UserSearchResponse
import com.project.tiket_test.data.utils.AppExecutors
import com.project.tiket_test.data.utils.NetworkPagedListResourcesLiveData
import com.project.tiket_test.data.utils.Resource
import com.project.tiket_test.domain.repository.ISearchUserRepository

class SearchUserRepository constructor(
    private val remoteDataSource: RemoteDataSource,
    private val appExecutors: AppExecutors
): ISearchUserRepository {

    override fun searchUser(query: String): LiveData<Resource<PagedList<UserItem>>> {
        return object : NetworkPagedListResourcesLiveData<UserItem, UserSearchResponse>(appExecutors){
            override fun createCall(currentPage: Int): LiveData<ApiResponse<UserSearchResponse>> {
                return remoteDataSource.searchUser(query, currentPage)
            }

            override fun convertToListType(remoteData: UserSearchResponse): List<UserItem> {
                return remoteData.items?: mutableListOf()
            }

            override fun getPageSize(): Int = PER_PAGE_SIZE / 2

            override fun isLastPage(remoteData: UserSearchResponse): Boolean {
                return remoteData.items?.size?:0 < PER_PAGE_SIZE
            }

        }.asLiveData()
    }
}