package com.stevendevit.music.songs

import com.stevendevit.music.songs.contract.ISongRepositoryContract

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
interface ISongDataSource : ISongRepositoryContract{

    suspend fun invalidateCache()
}

