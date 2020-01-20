package com.stevendevit.shared.usecase

import com.stevendevit.shared.data.Resource
import kotlinx.coroutines.CoroutineScope

abstract class ScopedUseCase<in params : UseCase.Params, out T : Any> :
    UseCase {

    abstract suspend fun execute(scope: CoroutineScope, parameters: params, failureHandler: FailureAlias): Resource<T>
}
