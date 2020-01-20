package com.stevendevit.music.album.cache

import com.stevendevit.music.Album
import com.stevendevit.music.paper.TableConstants
import com.stevendevit.shared.dispatcher.DefaultDispatcherProvider
import io.paperdb.Paper
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
class AlbumCacheRepository : IAlbumCacheRepository {

    override suspend fun getKeys(): List<String> {
        return Paper.book(TableConstants.TABLE_ALBUM).allKeys
    }

    override suspend fun saveAllAlbums(albums: List<Album>) =
        withContext(DefaultDispatcherProvider.io()) {

            albums.forEach {

                try {

                    saveAlbum(it)
                }catch (ex: Exception){

                }
            }
        }

    override suspend fun saveAlbum(album: Album) {
        withContext(DefaultDispatcherProvider.io())
        {
            Paper.book(TableConstants.TABLE_ALBUM)
                .write<Album>(album.title, album)
        }
    }

    override suspend fun getAllAlbums(sorted: Boolean): List<Album>? =
        withContext(DefaultDispatcherProvider.io()) {

            val keys = getKeys()

            return@withContext keys.mapNotNull {

                Paper.book(TableConstants.TABLE_ALBUM)
                    .read<Album>(it)
            }.toList()
        }

    override suspend fun getAllAlbumsByArtist(
        artist: String,
        sorted: Boolean
    ): List<Album>? =
        withContext(DefaultDispatcherProvider.io()) {

            return@withContext Paper.book(TableConstants.TABLE_ALBUM)
                .read<List<Album>>(artist)
        }
}