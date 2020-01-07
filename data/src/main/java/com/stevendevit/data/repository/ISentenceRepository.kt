package com.stevendevit.data.repository

import com.stevendevit.domain.model.SentenceMapEntry
import io.reactivex.rxjava3.core.Single

interface ISentenceRepository {

    fun loadAllSentences(): Single<List<SentenceMapEntry>>
    fun update(identifier: String, entry: SentenceMapEntry): Single<Boolean>
}