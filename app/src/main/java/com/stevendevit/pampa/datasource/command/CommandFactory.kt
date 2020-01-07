package com.stevendevit.pampa.datasource.command

import com.stevendevit.data.commands.AbstractCommand
import com.stevendevit.data.commands.NoOpCommand
import com.stevendevit.data.constants.CommandTableConstants
import com.stevendevit.domain.model.CommandMetaData
import java.util.*

/**
 * Created by stevendevit on 02/01/2020.
 * tankadeveloper@gmail.com
 */
sealed class CommandFactory {

    companion object {
        fun make(
            commandTable: ICommandTable,
            commandMetaData: CommandMetaData
        ): AbstractCommand<*> {

            return make(
                commandTable,
                commandMetaData.identifier,
                commandMetaData.params
            )
        }

        private fun make(
            commandTable: ICommandTable,
            identifier: String,
            params: List<String>
        ): AbstractCommand<*> {

            val command = CommandMetaData(identifier = identifier, params = params)

            return when {
                command.identifier.toLowerCase(Locale.ROOT) == commandTable.getFromCache(
                    CommandTableConstants.COMMAND_ID_VOLUME
                ).localisedIdentifier
                -> NoOpCommand(command)

                else -> NoOpCommand(command)
            }
        }
    }
}

