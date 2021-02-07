package com.project.tiket_test.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.tiket_test.data.sources.remote.model.UserItem
import com.project.tiket_test.data.utils.Resource
import com.project.tiket_test.domain.repository.ISearchUserRepository

class SearchUserInteractor constructor(private val searchUserRepository: ISearchUserRepository): SearchUserUseCase {
    override fun searchUser(query: String): LiveData<Resource<PagedList<UserItem>>> {
        return searchUserRepository.searchUser(query)
    }
}