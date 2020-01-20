package com.stevendevit.shared.cache

import java.util.*
import kotlin.collections.HashMap

/**
 * Created by stevendevit on 11/01/2020.
 * tankadeveloper@gmail.com
 */
interface ICache<K, V> {

    val cacheTable: HashMap<K, V>
}

interface IWeakCache<K, V> {

    val cacheTable: WeakHashMap<K, V>
}