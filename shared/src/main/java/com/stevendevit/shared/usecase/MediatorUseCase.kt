package com.stevendevit.shared.usecase

import androidx.lifecycle.MediatorLiveData
import com.stevendevit.shared.data.Resource
import kotlinx.coroutines.CoroutineScope

abstract class MediatorUseCase<in P, R> {

    protected val result = MediatorLiveData<Resource<R>>()

    open fun observe(): MediatorLiveData<Resource<R>> {
        return result
    }

    abstract fun execute(scope: CoroutineScope, parameters: P)
}
