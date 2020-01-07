package com.stevendevit.data.interfaces

import com.stevendevit.domain.model.CommandMetaData

/**
 * Created by stevendevit on 01/01/2020.
 * tankadeveloper@gmail.com
 */
interface IPlayerCommandHandler {

    fun perform(commandMetaData: CommandMetaData)
    fun setDelegate(delegate: ICommandCycleDelegate)
    fun clear()
}
