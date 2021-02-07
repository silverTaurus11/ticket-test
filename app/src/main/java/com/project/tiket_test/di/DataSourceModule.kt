package com.project.tiket_test.di

import com.project.tiket_test.data.sources.remote.RemoteDataSource
import com.project.tiket_test.data.utils.AppExecutors
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single { RemoteDataSource(get()) }
}

val appExecutorsModule = module {
    single { AppExecutors() }
}
