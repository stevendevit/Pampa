package com.stevendevit.shared.base

import androidx.lifecycle.MutableLiveData
import com.stevendevit.shared.composite.CompositeJob
import com.stevendevit.shared.usecase.Failure
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KFunction

interface IBaseViewModel {

    val compositeJob : CompositeJob

    val failureLiveData : MutableLiveData<Failure>

    fun runJob(kFun: KFunction<Any>, jobCallback: suspend CoroutineScope.() -> Unit)

    fun handleFailure(failure: Failure)
}