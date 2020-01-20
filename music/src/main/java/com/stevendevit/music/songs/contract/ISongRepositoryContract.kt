package com.stevendevit.music.songs.contract

import com.stevendevit.domain.model.Music

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
interface ISongRepositoryContract {

    suspend fun getAllSongs(): MutableList<Music>?
}