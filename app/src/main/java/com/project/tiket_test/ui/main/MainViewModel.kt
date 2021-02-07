package com.project.tiket_test.ui.main

import androidx.lifecycle.ViewModel
import com.project.tiket_test.domain.usecase.SearchUserUseCase
import com.project.tiket_test.ui.RefreshableLiveData

class MainViewModel constructor(private val useCase: SearchUserUseCase): ViewModel() {
    fun searchUser(query: String) = RefreshableLiveData {useCase.searchUser(query)}
}