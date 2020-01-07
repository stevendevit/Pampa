package com.stevendevit.pampa.datasource.repository

import com.stevendevit.data.constants.PaperConstants
import com.stevendevit.data.repository.INumberRepository
import com.stevendevit.domain.model.NumberMapEntry
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
class NumberRepositoryImpl : INumberRepository {

    override fun loadAllNumbers(): Single<List<NumberMapEntry>> {
        return Single.just(Paper.book().read<List<NumberMapEntry>>(PaperConstants.TABLE_NUMBERS))
    }

    override fun update(identifier: String, entry: NumberMapEntry): Single<Boolean> {

        return Single.just(loadAllNumbers()).flatMap<Boolean> { list ->

            val newItems =
                list.blockingGet().dropWhile { it.identifier == identifier }.plus(entry.copy())
            Paper.book().write(identifier, newItems)
            Single.just(true)
        }.doOnError {
            Single.error<Boolean>(it)
        }
    }
}