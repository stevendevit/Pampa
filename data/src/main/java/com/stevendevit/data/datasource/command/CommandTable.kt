package com.stevendevit.data.datasource.command

import com.stevendevit.domain.model.CommandMapEntry
import com.stevendevit.domain.model.CommandMetaData
import com.stevendevit.domain.repository.ICommandRepository
import com.stevendevit.domain.table.ICommandTable
import com.stevendevit.domain.table.ITable
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */
class CommandTable(private val repository: ICommandRepository) : ICommandTable,
    ITable<String, CommandMapEntry>() {

    override val table: HashMap<String, CommandMapEntry> by lazy { hashMapOf<String, CommandMapEntry>() }

    override fun load() {

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

    override fun getParameter(group: String, paramIdentifier: String): String {
        val commandGroup = getFromCache(group)
        return commandGroup.parameters.first { it.identifier == paramIdentifier }.localised
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
