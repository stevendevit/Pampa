package com.stevendevit.music.album.repo

import com.stevendevit.domain.model.Music
import com.stevendevit.music.Album
import com.stevendevit.music.songs.ISongDataSource
import com.stevendevit.shared.ext.whenNotNullNorEmpty

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
class AlbumRepository(private val songDataSource: ISongDataSource) :
    IAlbumRepository {

    override suspend fun getAllAlbums(sorted: Boolean): List<Album>? {

        val allSongs = songDataSource.getAllSongs()

        allSongs?.let {

            val allByArtist = allSongs.groupBy { it.artist }
            val allAlbumsByArtist = hashMapOf<String, List<Album>>()

            allByArtist.keys.iterator().forEach {

                allAlbumsByArtist[it!!] = buildAlbums(allByArtist.getValue(it))
            }

            val newValues = allAlbumsByArtist.map { it.value }.flatten()

            if (sorted)
                newValues.sortedBy { it.title }

            return newValues
        }

        return null
    }

    override suspend fun getAllAlbumsByArtist(
        artist: String,
        sorted: Boolean
    ): List<Album>? {

        val allAlbums = getAllAlbums(sorted)

        allAlbums.whenNotNullNorEmpty {

            val filteredAlbumsByArtist =
                it.filter { album -> album.music?.firstOrNull { song -> song.artist?.toLowerCase() ?: "" == artist.toLowerCase() } != null }
                    .toList()

            if (sorted)
                filteredAlbumsByArtist.sortedBy { album -> album.title }

            return filteredAlbumsByArtist
        }

        return null
    }

    private fun buildAlbums(artistSongs: List<Music>): List<Album> {

        val sortedAlbums = mutableListOf<Album>()

        try {

            val mappedSongs = artistSongs.groupBy { song -> song.album }
            mappedSongs.keys.forEach {

                val songs = mappedSongs.getValue(it).toMutableList()
                songs.sortBy { song -> song.track }

                val totalDuration =
                    songs.map { song -> song.duration }.fold(0.toLong()) { left, right ->
                        left + right
                    }

                sortedAlbums.add(
                    Album(
                        it,
                        "",
                        songs,
                        totalDuration
                    )
                )
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        sortedAlbums.sortBy { it.year }

        return sortedAlbums
    }
}