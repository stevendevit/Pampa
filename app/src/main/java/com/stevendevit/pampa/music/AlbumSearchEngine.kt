package com.stevendevit.pampa.music

import com.stevendevit.music.Album
import com.stevendevit.music.album.IAlbumDataSource
import com.stevendevit.shared.dispatcher.DefaultDispatcherProvider
import com.stevendevit.shared.ext.whenNotNullNorEmpty
import kotlinx.coroutines.withContext

/**
 * Created by stevendevit on 16/01/2020.
 * tankadeveloper@gmail.com
 */
class AlbumSearchEngine(private val albumDataSource: IAlbumDataSource) :
    IAlbumSearchEngine<AlbumSearchEngine.AlbumSearchRequest, AlbumSearchEngine.AlbumSearchResult> {

    override suspend fun perform(request: AlbumSearchRequest): AlbumSearchResult = withContext(
        DefaultDispatcherProvider.default()
    ) {

        val album = albumDataSource.getAllAlbumsByArtist(request.artist)

        album?.whenNotNullNorEmpty {

            return@withContext AlbumSearchResult(request, it, true)
        }

        return@withContext AlbumSearchResult(request, listOf(), false)
    }

    data class AlbumSearchResult(
        val request: AlbumSearchRequest,
        val albums: List<Album>,
        val found: Boolean
    )

    data class AlbumSearchRequest(val artist: String)
}