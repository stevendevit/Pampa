package com.stevendevit.domain.repository

import com.stevendevit.domain.model.NumberMapEntry
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
interface INumberRepository {
    fun loadAllNumbers(): Single<List<NumberMapEntry>>
    fun update(identifier: String, entry: NumberMapEntry): Single<Boolean>
}