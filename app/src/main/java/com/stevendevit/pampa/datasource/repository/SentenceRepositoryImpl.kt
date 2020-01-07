package com.stevendevit.pampa.datasource.repository

import com.stevendevit.data.constants.PaperConstants
import com.stevendevit.data.repository.ISentenceRepository
import com.stevendevit.domain.model.SentenceMapEntry
import com.stevendevit.domain.model.SentenceMapEntryValue
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
class SentenceRepositoryImpl : ISentenceRepository {

    override fun loadAllSentences(): Single<List<SentenceMapEntry>> {
        return Single.just(Paper.book().read<List<SentenceMapEntry>>(PaperConstants.TABLE_SENTENCES))
    }

    override fun update(identifier: String, entry: SentenceMapEntry): Single<Boolean> {
        return Single.just(loadAllSentences()).flatMap<Boolean> { list ->

            val newItems =
                list.blockingGet().dropWhile { it.identifier == identifier }.plus(entry.copy())
            Paper.book().write(identifier, newItems)
            Single.just(true)
        }.doOnError {
            Single.error<Boolean>(it)
        }
    }
}