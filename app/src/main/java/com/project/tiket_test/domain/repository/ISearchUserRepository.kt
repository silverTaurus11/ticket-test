package com.project.tiket_test.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.project.tiket_test.data.sources.remote.model.UserItem
import com.project.tiket_test.data.utils.Resource

interface ISearchUserRepository {
    fun searchUser(query: String): LiveData<Resource<PagedList<UserItem>>>
}