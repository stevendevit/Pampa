package com.stevendevit.domain.interfaces

import com.stevendevit.domain.model.CommandMetaData

/**
 * Created by stevendevit on 02/01/2020.
 * tankadeveloper@gmail.com
 */
interface ICommandManagerDelegate {

    fun onParamsNotValid(commandMetaData: CommandMetaData)
    fun onCommandNotRecognized(commandMetaData: CommandMetaData)
}
