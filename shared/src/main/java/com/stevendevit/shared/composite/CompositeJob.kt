package com.stevendevit.shared.composite

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class CompositeJob : ICompositeJob {

    override val jobs: WeakHashMap<String, Job> by lazy { WeakHashMap<String, Job>() }

    override fun add(
        identifier: String,
        scope: CoroutineScope,
        jobCallback: suspend CoroutineScope.() -> Unit
    ) {

        val existingJob = jobs.keys.firstOrNull { it == identifier }

        if (existingJob != null) return

        val job = scope.launch {
            jobCallback()
            cancelJob(identifier)
        }

        jobs[identifier] = job
    }

    private fun cancelJob(name: String) {

        try {
            jobs[name]?.cancel()
            jobs.remove(name)
        } catch (exception: Exception) {
        }
    }

    override fun clear() {

        try {
            jobs.map { it.value }.filter { !it.isCancelled }.forEach {
                it.cancel()
            }
            jobs.clear()
        } catch (ex: Exception) {
            // do nothing
        }
    }
}
