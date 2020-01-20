package com.stevendevit.music.playlist

import com.stevendevit.domain.model.Music
import com.stevendevit.music.PlayList

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
interface IPlayListRepository {

    suspend fun getAllPlayList(): List<PlayList>

    suspend fun getKeys(): List<String>

    suspend fun savePlayList(playList: PlayList) : Boolean

    suspend fun addSongToPlayList(name: String, song: Music) : Boolean
}