package com.stevendevit.pampa.command.handler

import android.content.Context
import com.stevendevit.data.commands.AbstractCommand
import com.stevendevit.domain.model.CommandMetaData
import com.stevendevit.pampa.datasource.command.CommandFactory
import com.stevendevit.pampa.datasource.command.ICommandTable

/**
 * Created by stevendevit on 01/01/2020.
 * tankadeveloper@gmail.com
 */
class DefaultPlayerCommandHandler(
    private val context: Context, private val commandTable: ICommandTable
) : AbstractPlayerCommandHandler() {

    override fun perform(commandMetaData: CommandMetaData) {

        val command: AbstractCommand<*> = CommandFactory.make(commandTable, commandMetaData)
        command.setup(context)

        val disposable = command.perform().subscribe({

            val result = it as AbstractCommand.AbstractCommandResult<*>
            _cycleDelegate?.onCommandPerformed(commandMetaData, result)

        }, {
            _cycleDelegate?.onError(commandMetaData, it)
            handleError(it)
        })

        compositeDisposable.add(disposable)
    }

    private fun handleError(throwable: Throwable) {

    }

    override fun clear() {
    }
}
