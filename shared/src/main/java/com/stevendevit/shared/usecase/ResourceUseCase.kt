package com.stevendevit.shared.usecase

import com.stevendevit.shared.data.Resource


abstract class ResourceUseCase<in params : UseCase.Params, out T : Any> : UseCase {

    abstract suspend fun executeAsync(params: params, failureHandler: FailureAlias): Resource<T>
}


