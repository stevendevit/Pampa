package com.stevendevit.pampa.datasource.number

import com.stevendevit.data.repository.INumberRepository
import com.stevendevit.domain.model.NumberMapEntry
import com.stevendevit.domain.table.ITable
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
class NumberTable(private val repository: INumberRepository) : INumberTable,
    ITable<String, NumberMapEntry>() {

    override lateinit var table: HashMap<String, NumberMapEntry>

    override fun load() {
        table = HashMap()
        repository.loadAllNumbers().subscribe { commands ->
            commands.forEach { entry ->
                this.table[entry.identifier] = entry
            }
        }
    }

    override fun getFromCache(key: String): NumberMapEntry {

        return table[key]!!
    }

    override fun mapFrom(localisedIdentifier: String): NumberMapEntry {

        val value = table.values.firstOrNull { it.localisedIdentifier == localisedIdentifier }
        return value!!
    }

    override fun isValidNumber(numberId: String): Boolean {
        return table.values.firstOrNull { it.localisedIdentifier == numberId } != null
    }

    override fun update(key: String, numberMapEntry: NumberMapEntry): Single<Boolean> {

        return repository.update(key, numberMapEntry).doOnSuccess {

            table[key]?.let {
                table[key] = it.copy()
            }
        }
    }
}

interface INumberTable {

    fun load()
    fun getFromCache(key: String): NumberMapEntry
    fun mapFrom(localisedIdentifier: String): NumberMapEntry
    fun isValidNumber(numberId: String): Boolean
    fun update(key: String, numberMapEntry: NumberMapEntry): Single<Boolean>
}