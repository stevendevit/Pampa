package com.stevendevit.shared.usecase

import com.stevendevit.shared.data.Resource

abstract class ResourceUseCaseNoParams<out T : Any> :
    UseCase {

    abstract suspend fun executeAsync(failureHandler: FailureAlias): Resource<T>
}