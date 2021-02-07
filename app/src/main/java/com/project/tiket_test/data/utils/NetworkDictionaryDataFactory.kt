package com.project.tiket_test.data.utils

import androidx.paging.DataSource

class NetworkDictionaryDataFactory<T>(private val dictionaryListener: NetworkDictionaryDataSource.DictionaryListener<T>)
    : DataSource.Factory<Int, T>() {

    private val items: MutableList<T> by lazy { mutableListOf() }

    private val dataSource by lazy { NetworkDictionaryDataSource(items, dictionaryListener) }


    override fun create(): DataSource<Int, T> {
        return dataSource
    }

    fun updateData(newItems: MutableList<T>){
        items.addAll(newItems)
    }

    fun getNetworkDataSource() = dataSource

}