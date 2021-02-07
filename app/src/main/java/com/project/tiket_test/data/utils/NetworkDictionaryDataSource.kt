package com.project.tiket_test.data.utils

import androidx.paging.PageKeyedDataSource

class NetworkDictionaryDataSource<T> constructor(private val items: MutableList<T>,
                                                 private val dictionaryListener: DictionaryListener<T>
)
    : PageKeyedDataSource<Int, T>() {

    private var currentPage = 1
    private var nextPage = currentPage + 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        callback.onResult(items, null, nextPage)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {}

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        currentPage = nextPage
        nextPage = currentPage + 1
        dictionaryListener.doCallBackLoadAfter(currentPage, nextPage, params, callback)
    }

    fun retry(params: LoadParams<Int>, callback: LoadCallback<Int, T>){
        nextPage -= 1
        loadAfter(params, callback)
    }

    interface DictionaryListener<T>{
        fun doCallBackLoadAfter(currentPage: Int,
                                nextPage: Int,
                                params: LoadParams<Int>,
                                callback: LoadCallback<Int, T>)
    }

}