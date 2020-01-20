package com.stevendevit.shared.ext

import com.stevendevit.shared.usecase.Failure
import com.stevendevit.shared.usecase.FailureEmpty

/**
 * Created by stevendevit on 11/01/2020.
 * tankadeveloper@gmail.com
 */
fun String?.getOrEmpty(): String {

    this?.let {
        return it
    }

    return ""
}


fun Exception?.getMessageOrEmpty(): String {

    this?.let { it ->

        it.message?.let { msg ->
            return msg
        }
    }

    return ""
}

fun Failure?.get(): Failure {

    this?.let { it ->

        return this
    }

    return FailureEmpty()
}