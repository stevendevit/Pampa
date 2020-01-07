package com.stevendevit.pampa.command.handler

import com.stevendevit.data.interfaces.ICommandCycleDelegate
import com.stevendevit.data.interfaces.IPlayerCommandHandler
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * Created by stevendevit on 01/01/2020.
 * tankadeveloper@gmail.com
 */
abstract class AbstractPlayerCommandHandler : IPlayerCommandHandler {

    val compositeDisposable by lazy {
        CompositeDisposable()
    }

    var _cycleDelegate: ICommandCycleDelegate? = null

    override fun setDelegate(delegate: ICommandCycleDelegate) {
        _cycleDelegate = delegate
    }

    override fun clear() {

        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
            compositeDisposable.clear()
        }
    }
}
