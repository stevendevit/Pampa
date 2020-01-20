package com.stevendevit.pampa.behavior

import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * Created by stevendevit on 02/01/2020.
 * tankadeveloper@gmail.com
 */
abstract class AbstractBehavior {

    protected val compositeDisposable by lazy {
        CompositeDisposable()
    }

    abstract fun perform()

    fun clear() {

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
            compositeDisposable.clear()
        }
    }
}
