package com.stevendevit.shared.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stevendevit.shared.composite.CompositeJob
import com.stevendevit.shared.usecase.Failure
import kotlinx.coroutines.CoroutineScope
import kotlin.reflect.KFunction

open class BaseViewModel : ViewModel(), IBaseViewModel {

    override val compositeJob: CompositeJob
            by lazy { CompositeJob() }

    private val _failure: MutableLiveData<Failure> by lazy {
        MutableLiveData<Failure>()
    }

    override val failureLiveData: MutableLiveData<Failure>
        get() = _failure

    override fun runJob(kFun: KFunction<Any>, jobCallback: suspend CoroutineScope.() -> Unit) {

        val kFunName = kFun.name
        compositeJob.add(kFunName, viewModelScope, jobCallback)
    }

    override fun handleFailure(failure: Failure) {
        _failure.postValue(failure)
    }

    override fun onCleared() {
        super.onCleared()
        compositeJob.clear()
    }
}

