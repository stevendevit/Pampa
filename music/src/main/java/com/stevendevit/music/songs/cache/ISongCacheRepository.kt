package com.stevendevit.music.songs.cache

import com.stevendevit.domain.model.Music
import com.stevendevit.music.songs.contract.ISongRepositoryContract

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
interface ISongCacheRepository : ISongRepositoryContract {

    suspend fun addNewSong(music : Music) : Boolean

    suspend fun saveAllSongs(musicList: MutableList<Music>) : Boolean
}