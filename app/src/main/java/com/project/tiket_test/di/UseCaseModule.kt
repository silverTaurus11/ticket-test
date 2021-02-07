package com.project.tiket_test.di

import com.project.tiket_test.domain.usecase.SearchUserInteractor
import com.project.tiket_test.domain.usecase.SearchUserUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single<SearchUserUseCase> { SearchUserInteractor(get())}
}