package com.stevendevit.domain.table

import com.stevendevit.domain.model.NumberMapEntry
import io.reactivex.rxjava3.core.Single

interface INumberTable {

    fun load()
    fun getFromCache(key: String): NumberMapEntry
    fun mapFrom(localisedIdentifier: String): NumberMapEntry
    fun getNumber(localisedIdentifier: String): Int
    fun isValidNumber(localisedId: String): Boolean
    fun update(key: String, numberMapEntry: NumberMapEntry): Single<Boolean>
}