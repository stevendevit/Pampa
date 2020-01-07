package com.stevendevit.data.model

import com.stevendevit.domain.model.SubCommandMapEntry

/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */
data class CommandJsonValue(
    val command: String,
    val localised: String,
    val subCommands : List<SubCommandMapEntry>
)