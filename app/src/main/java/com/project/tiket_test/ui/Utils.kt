package com.project.tiket_test.ui

import android.content.res.Resources
import android.view.View
import com.project.tiket_test.R
import com.project.tiket_test.data.utils.GithubError
import retrofit2.HttpException


fun View.toVisible(isVisible: Boolean){
    if(isVisible){
        this.visibility = View.VISIBLE
    } else{
        this.visibility = View.GONE
    }
}

fun errorCodeTranslate(resources: Resources, throwable: Throwable?): String{
    if(throwable is HttpException){
        return when(throwable.code()){
            GithubError.GITHUB_REACH_REQUEST_LIMIT_CODE -> {
                resources.getString(R.string.error_request_limit)
            }
            GithubError.GITHUB_INVALID_QUERY_CODE -> {
                resources.getString(R.string.invalid_keyword)
            }
            else -> ""
        }
    }
    return ""
}