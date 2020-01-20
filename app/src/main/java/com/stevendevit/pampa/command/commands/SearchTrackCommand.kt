package com.stevendevit.pampa.command.commands

import com.stevendevit.data.constants.CommandsConstants
import com.stevendevit.domain.commands.AbstractCommand
import com.stevendevit.domain.model.CommandMetaData
import com.stevendevit.pampa.command.exception.Exceptions
import com.stevendevit.pampa.module.KoinApp
import com.stevendevit.pampa.music.SongSearchEngine
import com.stevendevit.shared.data.Either

/**
 * Created by stevendevit on 02/01/2020.
 * tankadeveloper@gmail.com
 */

class SearchTrackCommand(commandMetaData: CommandMetaData) :
    AbstractCommand<Either<SearchTrackCommand.SearchTrackCommandResult.Error, SearchTrackCommand.SearchTrackCommandResult.Success>>(
        commandMetaData
    ) {

    override suspend fun perform(): Either<SearchTrackCommandResult.Error, SearchTrackCommandResult.Success> {

        var query = commandMetaData.params[0]

        if (query.isEmpty()) {
            return Either.Left(SearchTrackCommandResult.Error(Exceptions.CommandNotRecognizedException()))
        }

        val localisedIgnoreParameter = KoinApp.CommandTable.getParameter(
            CommandsConstants.CommandGroup.COMMAND_ID_SEARCH_TRACK,
            CommandsConstants.CommandParameter.SEARCH_TRACK_PARAM_IGNORE
        )

        val hasIgnore = query == localisedIgnoreParameter

        if (hasIgnore) {

            query = commandMetaData.params[1]
        }

        val result = KoinApp.SongSearchEngine.perform(SongSearchEngine.SongSearchRequest(query))

        return Either.Right(SearchTrackCommandResult.Success(hasIgnore, result))
    }

    sealed class SearchTrackCommandResult : Result<SearchTrackCommandResult> {

        data class Success(
            val ignore: Boolean,
            val data: SongSearchEngine.SongSearchResult
        ) :
            SearchTrackCommandResult()

        class Error(err: Throwable) : Failure(err)
    }
}
