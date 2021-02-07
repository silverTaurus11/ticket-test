package com.project.tiket_test.data.utils

import android.view.View

sealed class Resource<T>(var data: T? = null,
                         val message: String? = null,
                         val throwable: Throwable?= null,
                         val isLoadMoreBehaviour: Boolean = false) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(data: T? = null,
                   message: String,
                   throwable: Throwable?= null,
                   isLoadMoreError: Boolean = false,
                   val retry: View.OnClickListener?= null) : Resource<T>(data, message, throwable, isLoadMoreError)
    class Empty<T>(isLoadMoreBehaviour: Boolean = false): Resource<T>(isLoadMoreBehaviour = isLoadMoreBehaviour)
    class LoadMoreLoading<T>: Resource<T>()
}
