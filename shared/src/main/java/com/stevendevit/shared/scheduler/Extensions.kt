package com.stevendevit.shared.scheduler

import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 13/01/2020.
 * tankadeveloper@gmail.com
 */

fun <T> Single<T>.defaultIoMain(): Single<T> {

    return this.subscribeOn(DefaultSchedulerProvider.io())
        .observeOn(DefaultSchedulerProvider.main())
}

fun <T> Single<T>.defaultThreadMain(): Single<T> {

    this.subscribeOn(DefaultSchedulerProvider.io()).observeOn(DefaultSchedulerProvider.main())
    return this
}