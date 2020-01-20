package com.stevendevit.music

import com.stevendevit.domain.model.Music

/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */
data class PlayList(val identifier: String, val totalDuration: Long, val list: List<Music>)