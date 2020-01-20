package com.stevendevit.pampa.command.handler

import com.stevendevit.domain.interfaces.ICommandCycleDelegate
import com.stevendevit.domain.interfaces.IPlayerCommandHandler
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * Created by stevendevit on 01/01/2020.
 * tankadeveloper@gmail.com
 */
 abstract class AbstractPlayerCommandHandler : IPlayerCommandHandler {

    var _cycleDelegate: ICommandCycleDelegate? = null

    override fun setDelegate(delegate: ICommandCycleDelegate) {
        _cycleDelegate = delegate
    }

    override fun clear() {

    }

    override fun dispose() {
    }
}
