package com.stevendevit.data.repository

import com.stevendevit.domain.model.CommandMapEntry
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */
interface ICommandRepository {

    fun loadAllCommands(): Single<List<CommandMapEntry>>
    fun update(identifier: String, entry: CommandMapEntry): Single<Boolean>
}

