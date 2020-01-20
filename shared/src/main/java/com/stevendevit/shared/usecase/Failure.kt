package com.stevendevit.shared.usecase

abstract class Failure(val failureCause: String) : Exception()

class FailureEmpty(cause: String = "") : Failure(cause)

typealias FailureAlias = (failure: Failure) -> Unit

