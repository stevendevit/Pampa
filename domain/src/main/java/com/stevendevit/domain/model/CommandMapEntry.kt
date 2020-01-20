package com.stevendevit.domain.model

/**
 * Created by stevendevit on 03/01/2020.
 * tankadeveloper@gmail.com
 */

data class CommandMapEntry(
    val identifier: String, val localisedIdentifier: String,
    val subCommands: List<SubCommandMapEntry>,
    val parameters : List<CommandParameterEntryValue>
)

data class CommandParameterEntryValue(
    val identifier: String, val localised: String
)

