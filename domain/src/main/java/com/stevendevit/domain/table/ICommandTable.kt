package com.stevendevit.domain.table

import com.stevendevit.domain.model.CommandMapEntry
import com.stevendevit.domain.model.CommandMetaData
import io.reactivex.rxjava3.core.Single

interface ICommandTable {

    fun load()
    fun getFromCache(key: String): CommandMapEntry
    fun mapFrom(localisedIdentifier: String): CommandMapEntry
    fun getParameter(group: String, paramIdentifier : String) : String
    fun isValidCommand(commandMetaData: CommandMetaData): Boolean
    fun update(key: String, commandMapEntry: CommandMapEntry): Single<Boolean>
}