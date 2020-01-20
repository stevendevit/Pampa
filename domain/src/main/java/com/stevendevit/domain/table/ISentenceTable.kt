package com.stevendevit.domain.table

import com.stevendevit.domain.model.SentenceMapEntry
import io.reactivex.rxjava3.core.Single

interface ISentenceTable {

    fun load()
    fun getFromCache(key: String): SentenceMapEntry
    fun getSentence(group: String, identifier: String): String
    fun update(key: String, sentenceMapEntry: SentenceMapEntry): Single<Boolean>
}