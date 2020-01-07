package com.stevendevit.domain.model

data class Music(
    val artist: String?,
    val year: Int,
    val track: Int,
    val title: String?,
    val duration: Long,
    val album: String?,
    val path: String?,
    val albumId: String?
)