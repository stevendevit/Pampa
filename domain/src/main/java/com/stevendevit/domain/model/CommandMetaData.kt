package com.stevendevit.domain.model

/**
 * Created by stevendevit on 01/01/2020.
 * tankadeveloper@gmail.com
 */
data class CommandMetaData(val identifier: String, val params: List<String>) {

    companion object {
        val EMPTY : CommandMetaData = CommandMetaData("", listOf())
    }
}
