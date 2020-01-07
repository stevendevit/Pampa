package com.stevendevit.pampa.datasource.command

import com.stevendevit.data.repository.ICommandRepository
import com.stevendevit.domain.model.CommandMapEntry
import com.stevendevit.domain.model.CommandMetaData
import com.stevendevit.domain.table.ITable
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */
class CommandTable(private val repository: ICommandRepository) : ICommandTable,
    ITable<String, CommandMapEntry>() {

    override lateinit var table: HashMap<String, CommandMapEntry>

    override fun load() {

        table = HashMap()
        repository.loadAllCommands().subscribe { commands ->
            commands.forEach { entry ->
                this.table[entry.identifier] = entry
            }
        }
    }

    override fun getFromCache(key: String): CommandMapEntry {
        return table[key]!!
    }

    override fun mapFrom(localisedIdentifier: String): CommandMapEntry {
        val value = table.values.firstOrNull { it.localisedIdentifier == localisedIdentifier }
        return value!!
    }

    override fun update(key: String, commandMapEntry: CommandMapEntry): Single<Boolean> {

        return repository.update(key, commandMapEntry).doOnSuccess {

            table[key]?.let {
                table[key] = it.copy()
            }
        }
    }

    override fun isValidCommand(commandMetaData: CommandMetaData): Boolean {
        return table.values.firstOrNull { it.localisedIdentifier == commandMetaData.identifier } != null
    }
}

interface ICommandTable {

    fun load()
    fun getFromCache(key: String): CommandMapEntry
    fun mapFrom(localisedIdentifier: String): CommandMapEntry
    fun isValidCommand(commandMetaData: CommandMetaData): Boolean
    fun update(key: String, commandMapEntry: CommandMapEntry): Single<Boolean>
}