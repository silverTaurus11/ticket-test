package com.project.tiket_test.di

import com.project.tiket_test.data.repository.SearchUserRepository
import com.project.tiket_test.domain.repository.ISearchUserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<ISearchUserRepository> { SearchUserRepository(get(), get())}
}