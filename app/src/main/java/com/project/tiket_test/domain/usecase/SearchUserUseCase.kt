package com.project.tiket_test.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.tiket_test.data.sources.remote.model.UserItem
import com.project.tiket_test.data.utils.Resource

interface SearchUserUseCase {
    fun searchUser(query: String): LiveData<Resource<PagedList<UserItem>>>
}