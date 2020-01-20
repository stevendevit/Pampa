package com.stevendevit.data.constants

/**
 * Created by stevendevit on 18/01/2020.
 * tankadeveloper@gmail.com
 */
object CommandsConstants {

    object CommandGroup {

        const val COMMAND_ID_PLAYBACK = "COMMAND_ID_PLAYBACK"
        const val COMMAND_ID_TRACK = "COMMAND_ID_TRACK"
        const val COMMAND_ID_VOLUME = "COMMAND_ID_VOLUME"
        const val COMMAND_ID_SEARCH_TRACK = "COMMAND_ID_SEARCH_TRACK"
        const val CMD_NOT_RECOGNIZED = "CMD_NOT_RECOGNIZED"
    }

    object CommandGroupValue {

        const val SUB_CMD_TRACK_GO_BACK = "SUB_CMD_TRACK_GO_BACK"
        const val SUB_CMD_TRACK_GO_FORWARD = "SUB_CMD_TRACK_GO_FORWARD"
    }

    object CommandParameter {

        const val SEARCH_TRACK_PARAM_IGNORE = "SEARCH_TRACK_PARAM_IGNORE"
    }
}