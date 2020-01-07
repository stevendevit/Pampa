package com.stevendevit.pampa.datasource.sentence

import com.stevendevit.data.repository.ISentenceRepository
import com.stevendevit.domain.model.SentenceMapEntry
import com.stevendevit.domain.table.ITable
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
class SentenceTable(private val repository: ISentenceRepository) : ISentenceTable, ITable<String, SentenceMapEntry>() {

    override lateinit var table: HashMap<String, SentenceMapEntry>

    override fun load() {
        table = HashMap()
        repository.loadAllSentences().subscribe { commands ->
            commands.forEach { entry ->
                this.table[entry.identifier] = entry
            }
        }
    }

    override fun getFromCache(key: String): SentenceMapEntry {
        return table[key]!!
    }

    override fun update(key: String, sentenceMapEntry: SentenceMapEntry): Single<Boolean> {

        return repository.update(key, sentenceMapEntry).doOnSuccess {

            table[key]?.let {
                table[key] = it.copy()
            }
        }
    }
}

interface ISentenceTable {

    fun load()
    fun getFromCache(key: String): SentenceMapEntry
    fun update(key: String, sentenceMapEntry: SentenceMapEntry): Single<Boolean>
}