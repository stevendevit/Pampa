package com.stevendevit.data.constants

/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */
object CommandTableConstants {

    const val COMMAND_ID_PLAYBACK = "cmd_playback"
    const val COMMAND_ID_SEARCH_TRACK = "cmd_search_track"
    const val COMMAND_ID_TRACK = "cmd_track"
    const val COMMAND_ID_VOLUME = "cmd_volume"
}

object SubCommandTableConstants {

    const val TRACK_SUB_COMMAND_GO_BACK = "sub_cmd_go_back"
    const val TRACK_SUB_COMMAND_GO_FORWARD = "sub_cmd_go_forward"
}

const val CMD_NOT_RECOGNIZED = "CMD_NOT_RECOGNIZED"
const val CMD_PARAMS_NOT_VALID = "CMD_PARAMS_NOT_VALID"
const val SUB_CMD_NOT_RECOGNIZED = "SUB_CMD_NOT_RECOGNIZED"