package com.stevendevit.data.datasource.repository

import com.stevendevit.data.constants.PaperConstants
import com.stevendevit.domain.repository.ICommandRepository
import com.stevendevit.domain.model.CommandMapEntry
import com.stevendevit.shared.scheduler.defaultIoMain
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */
class CommandRepositoryImpl : ICommandRepository {

    override fun update(identifier: String, entry: CommandMapEntry): Single<Boolean> {

        return Single.just(loadAllCommands()).flatMap<Boolean> { list ->

            val newItems =
                list.blockingGet().dropWhile { it.identifier == identifier }.plus(entry.copy())
            Paper.book().write(identifier, newItems)
            Single.just(true)
        }.defaultIoMain()
            .doOnError {
                Single.error<Boolean>(it)
            }
    }

    override fun loadAllCommands(): Single<List<CommandMapEntry>> {
        return Single.just(Paper.book().read<List<CommandMapEntry>>(PaperConstants.TABLE_COMMANDS))
    }
}