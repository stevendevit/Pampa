package com.stevendevit.domain.table

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
abstract class ITable<K, V> {

    abstract val table : HashMap<K, V>
}