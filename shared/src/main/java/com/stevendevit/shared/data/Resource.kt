package com.stevendevit.shared.data

import com.stevendevit.shared.usecase.Failure

data class Resource<out T>(val status: ResourceStatus, val data: T?, val failure: Failure?) {

    companion object {
        fun <T> success(data: T? = null): Resource<T> {
            return Resource(
                ResourceStatus.SUCCESS,
                data, null
            )
        }

        fun <T> error(failure: Failure): Resource<T> {
            return Resource(
                ResourceStatus.ERROR,
                null,
                failure
            )
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                ResourceStatus.LOADING,
                data,
                null
            )
        }

        fun <T> empty(failure: Failure?): Resource<T> {
            return Resource(
                ResourceStatus.EMPTY,
                null,
                failure
            )
        }

        fun <T> empty(): Resource<T> {
            return Resource(
                ResourceStatus.EMPTY,
                null,
                null
            )
        }
    }
}

enum class ResourceStatus {
    SUCCESS,
    ERROR,
    LOADING,
    EMPTY
}

