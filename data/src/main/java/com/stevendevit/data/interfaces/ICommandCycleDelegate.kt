package com.stevendevit.data.interfaces

import com.stevendevit.data.commands.AbstractCommand
import com.stevendevit.domain.model.CommandMetaData

/**
 * Created by stevendevit on 02/01/2020.
 * tankadeveloper@gmail.com
 */
interface ICommandCycleDelegate {

    fun onError(commandMetaData: CommandMetaData, throwable: Throwable)
    fun onCommandPerformed(commandMetaData: CommandMetaData, result: AbstractCommand.AbstractCommandResult<*>)
}
