package com.stevendevit.shared.data

sealed class Either<out L, out R> {
 
    data class Left<out L>(val l: L) : Either<L, Nothing>()
 
    data class Right<out R>(val r: R) : Either<Nothing, R>()
}