package com.stevendevit.music.album.cache

import com.stevendevit.music.Album
import com.stevendevit.music.album.contract.IAlbumRepositoryContract

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
interface IAlbumCacheRepository : IAlbumRepositoryContract {

    suspend fun getKeys() : List<String>

    suspend fun saveAllAlbums(albums : List<Album>)

    suspend fun saveAlbum(album : Album)
}