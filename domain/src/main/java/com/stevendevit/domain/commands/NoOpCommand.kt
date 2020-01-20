package com.stevendevit.domain.commands

import com.stevendevit.domain.model.CommandMetaData
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable

/**
 * Created by stevendevit on 02/01/2020.
 * tankadeveloper@gmail.com
 */
class NoOpCommand(commandMetaData: CommandMetaData) : AbstractCommand<Unit>(commandMetaData) {
    override suspend fun perform(): Unit {
    }
}
