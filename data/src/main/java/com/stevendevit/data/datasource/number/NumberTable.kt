package com.stevendevit.data.datasource.number

import com.stevendevit.domain.repository.INumberRepository
import com.stevendevit.domain.model.NumberMapEntry
import com.stevendevit.domain.table.INumberTable
import com.stevendevit.domain.table.ITable
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
class NumberTable(private val repository: INumberRepository) : INumberTable,
    ITable<String, NumberMapEntry>() {

    override val table: HashMap<String, NumberMapEntry> by lazy { hashMapOf<String, NumberMapEntry>() }

    override fun load() {
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

    override fun getNumber(localisedIdentifier: String): Int {

        return mapFrom(localisedIdentifier).value
    }

    override fun isValidNumber(localisedId: String): Boolean {
        return table.values.firstOrNull { it.localisedIdentifier == localisedId } != null
    }

    override fun update(key: String, numberMapEntry: NumberMapEntry): Single<Boolean> {

        return repository.update(key, numberMapEntry).doOnSuccess {

            table[key]?.let {
                table[key] = it.copy()
            }
        }
    }
}

