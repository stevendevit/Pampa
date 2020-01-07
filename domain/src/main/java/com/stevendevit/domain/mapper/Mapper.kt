package com.stevendevit.domain.mapper

/**
 * Created by stevendevit on 02/01/2020.
 * tankadeveloper@gmail.com
 */
abstract class Mapper<K, V> {

    abstract var dictionary : HashMap<K, V>
    abstract fun load()
    abstract fun from(key : K) : V
}
