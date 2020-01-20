package com.stevendevit.shared.composite

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import java.util.*

interface ICompositeJob {

    val jobs : WeakHashMap<String, Job>

    fun add(
        identifier: String,
        scope: CoroutineScope,
        jobCallback: suspend CoroutineScope.() -> Unit
    )

    fun clear()
}