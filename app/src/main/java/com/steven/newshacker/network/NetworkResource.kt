package com.steven.newshacker.network


import com.mindorks.retrofit.coroutines.utils.Status.ERROR
import com.mindorks.retrofit.coroutines.utils.Status.LOADING
import com.mindorks.retrofit.coroutines.utils.Status.SUCCESS
import com.mindorks.retrofit.coroutines.utils.Status

data class NetworkResource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): NetworkResource<T> = NetworkResource(status = SUCCESS, data = data, message = null)

        fun <T> error(data: T?, message: String): NetworkResource<T> =
                NetworkResource(status = ERROR, data = data, message = message)

        fun <T> loading(data: T?): NetworkResource<T> = NetworkResource(status = LOADING, data = data, message = null)
    }
}