package com.stevendevit.music.album

import com.stevendevit.music.album.contract.IAlbumRepositoryContract

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
interface IAlbumDataSource : IAlbumRepositoryContract {

    suspend fun invalidateCache()
}