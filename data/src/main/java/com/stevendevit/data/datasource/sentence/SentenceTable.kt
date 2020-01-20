package com.stevendevit.data.datasource.sentence

import com.stevendevit.domain.repository.ISentenceRepository
import com.stevendevit.domain.model.SentenceMapEntry
import com.stevendevit.domain.table.ISentenceTable
import com.stevendevit.domain.table.ITable
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
class SentenceTable(private val repository: ISentenceRepository) : ISentenceTable,
    ITable<String, SentenceMapEntry>() {

    override val table: HashMap<String, SentenceMapEntry> by lazy { hashMapOf<String, SentenceMapEntry>() }

    override fun load() {
        repository.loadAllSentences().subscribe { commands ->
            commands.forEach { entry ->
                this.table[entry.identifier] = entry
            }
        }
    }

    override fun getSentence(group: String, identifier: String): String {
        val sentenceGroup = getFromCache(group)
        return sentenceGroup.sentences.first { it.identifier == identifier }.localised
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
