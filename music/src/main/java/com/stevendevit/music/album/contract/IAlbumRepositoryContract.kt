package com.stevendevit.music.album.contract

import com.stevendevit.music.Album

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
interface IAlbumRepositoryContract {
    suspend fun getAllAlbums(sorted: Boolean = true): List<Album>?
    suspend fun getAllAlbumsByArtist(artist: String, sorted: Boolean = true): List<Album>?
}