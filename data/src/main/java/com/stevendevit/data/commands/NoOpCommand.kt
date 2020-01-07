package com.stevendevit.data.commands

import com.stevendevit.domain.model.CommandMetaData
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable

/**
 * Created by stevendevit on 02/01/2020.
 * tankadeveloper@gmail.com
 */
class NoOpCommand(commandMetaData: CommandMetaData) : AbstractCommand<Nothing>(commandMetaData) {
    override fun perform(): @NonNull Observable<out Nothing> {
        return Observable.empty()
    }
}
