package com.stevendevit.pampa.command.manager

import com.stevendevit.data.interfaces.ICommandManagerDelegate
import com.stevendevit.data.interfaces.ICommandCycleDelegate
import com.stevendevit.data.interfaces.IPlayerCommandHandler
import com.stevendevit.domain.model.CommandMetaData
import com.stevendevit.pampa.datasource.command.ICommandTable
import java.util.*

/**
 * Created by stevendevit on 01/01/2020.
 * tankadeveloper@gmail.com
 */
class CommandManager(private val commandTable: ICommandTable) {

    private val commandQueue: ArrayDeque<CommandMetaData> = ArrayDeque()

    private lateinit var commandHandler: IPlayerCommandHandler
    private lateinit var commandManagerDelegate: ICommandManagerDelegate

    fun assignHandler(handler: IPlayerCommandHandler): CommandManager {

        this.commandHandler = handler
        return this
    }

    fun assignCommandCycleDelegate(delegate: ICommandCycleDelegate): CommandManager {

        this.commandHandler.setDelegate(delegate)
        return this
    }

    fun assignManagerDelegate(managerDelegate: ICommandManagerDelegate): CommandManager {

        this.commandManagerDelegate = managerDelegate
        return this
    }

    fun perform(commandMetaData: CommandMetaData) {

        if (!commandTable.isValidCommand(commandMetaData)) {
            commandManagerDelegate.onCommandNotRecognized(commandMetaData)
            return
        }

        this.commandQueue.add(commandMetaData)
        dequeCommands()
    }

    fun dequeCommands() {

        if (this.commandQueue.size >= 1) {
            val commandMeta = commandQueue.pop()
            this.commandHandler.perform(commandMeta)
        }
    }

    fun performBatch(list: List<String>){

        list.forEach {
            perform(it)
        }
    }

    fun perform(raw: String) {
        val parts = raw.split(" ")

        if (parts.size > 1) {
            this.perform(constructCommand(parts))
        } else {
            commandManagerDelegate.onParamsNotValid(constructCommand(parts))
        }
    }

    private fun constructCommand(params: List<String>): CommandMetaData {

        return CommandMetaData(identifier = params[0], params = params.drop(1))
    }
}
