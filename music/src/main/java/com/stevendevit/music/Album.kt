package com.stevendevit.music

import com.stevendevit.domain.model.Music

data class Album(
    val title: String?,
    val year: String?,
    val music: MutableList<Music>?,
    val totalDuration: Long
)
