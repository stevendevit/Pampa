package com.stevendevit.music.album

import com.stevendevit.music.Album
import com.stevendevit.music.album.cache.IAlbumCacheRepository
import com.stevendevit.music.album.repo.IAlbumRepository
import com.stevendevit.music.paper.TableConstants
import com.stevendevit.shared.dispatcher.DefaultDispatcherProvider
import com.stevendevit.shared.ext.whenNotNullNorEmpty
import io.paperdb.Paper
import kotlinx.coroutines.withContext

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
class AlbumDataSource(
    private val cacheRepository: IAlbumCacheRepository,
    private val repository: IAlbumRepository
) : IAlbumDataSource {

    override suspend fun getAllAlbums(sorted: Boolean): List<Album>? =
        withContext(DefaultDispatcherProvider.io()) {

            var allAlbums = cacheRepository.getAllAlbums(sorted)

            allAlbums?.whenNotNullNorEmpty {
                return@withContext it
            }

            allAlbums = repository.getAllAlbums(sorted)

            allAlbums?.whenNotNullNorEmpty {
                cacheRepository.saveAllAlbums(it)
            }

            return@withContext allAlbums
        }

    override suspend fun getAllAlbumsByArtist(
        artist: String,
        sorted: Boolean
    ): List<Album>? =
        withContext(DefaultDispatcherProvider.io()) {

            return@withContext repository.getAllAlbumsByArtist(artist, sorted)
        }

    override suspend fun invalidateCache() = withContext(DefaultDispatcherProvider.io()) {
        Paper.book(TableConstants.TABLE_ALBUM)
            .destroy()
    }
}