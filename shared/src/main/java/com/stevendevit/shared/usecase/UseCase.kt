package com.stevendevit.shared.usecase

interface UseCase {

    abstract class Params
}

//interface ResourceUseCaseNoParams<out T : Any> :
//    UseCase {
//    suspend fun executeAsync(): Resource<T>
//}
//interface ResourceUseCase<in params : UseCase.Params, out T : Any> :
//    UseCase {
//    suspend fun executeAsync(params: params): Resource<T>
//}