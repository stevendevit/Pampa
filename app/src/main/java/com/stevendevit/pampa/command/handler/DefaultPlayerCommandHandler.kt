package com.stevendevit.pampa.command.handler

import android.content.Context
import com.stevendevit.domain.commands.AbstractCommand
import com.stevendevit.domain.model.CommandMetaData
import com.stevendevit.pampa.command.CommandFactory
import com.stevendevit.domain.table.ICommandTable
import com.stevendevit.shared.data.Either

/**
 * Created by stevendevit on 01/01/2020.
 * tankadeveloper@gmail.com
 */
class DefaultPlayerCommandHandler(
    private val context: Context, private val commandTable: ICommandTable
) : AbstractPlayerCommandHandler() {

    override suspend fun perform(commandMetaData: CommandMetaData) {

        val command: AbstractCommand<*> = CommandFactory.make(commandTable, commandMetaData)
        command.setup(context)

        when (val result = command.perform() as Either<*, *>) {

            is Either.Left -> _cycleDelegate?.onError(commandMetaData, (result.l as AbstractCommand.Failure).throwable)
            is Either.Right -> _cycleDelegate?.onCommandPerformed(commandMetaData, result.r as AbstractCommand.Result<*>)
        }
    }

    private fun handleError(throwable: Throwable) {

    }
}
