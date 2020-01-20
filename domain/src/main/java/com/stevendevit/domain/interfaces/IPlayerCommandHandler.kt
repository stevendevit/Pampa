package com.stevendevit.domain.interfaces

import com.stevendevit.domain.model.CommandMetaData

/**
 * Created by stevendevit on 01/01/2020.
 * tankadeveloper@gmail.com
 */
interface IPlayerCommandHandler {

    suspend fun perform(commandMetaData: CommandMetaData)
    fun setDelegate(delegate: ICommandCycleDelegate)
    fun clear()
    fun dispose()
}
