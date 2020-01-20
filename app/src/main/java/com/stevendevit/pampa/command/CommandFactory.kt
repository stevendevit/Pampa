package com.stevendevit.pampa.command

import com.stevendevit.domain.commands.AbstractCommand
import com.stevendevit.domain.commands.NoOpCommand
import com.stevendevit.data.constants.CommandTableConstants
import com.stevendevit.domain.model.CommandMetaData
import com.stevendevit.domain.table.ICommandTable
import com.stevendevit.pampa.command.commands.SearchTrackCommand
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

                command.identifier.toLowerCase(Locale.ROOT) == commandTable.getFromCache(
                    CommandTableConstants.COMMAND_ID_SEARCH_TRACK
                ).localisedIdentifier -> SearchTrackCommand(command)

                else -> NoOpCommand(command)
            }
        }
    }
}

