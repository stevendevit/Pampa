package com.stevendevit.pampa.music

import com.stevendevit.domain.model.Music
import com.stevendevit.music.songs.ISongDataSource
import com.stevendevit.pampa.music.table.ISongSearchTable
import com.stevendevit.pampa.util.removeDiacriticalMarks
import com.stevendevit.shared.dispatcher.DefaultDispatcherProvider
import com.stevendevit.shared.ext.isTrue
import com.stevendevit.shared.ext.toMD5
import kotlinx.coroutines.withContext

/**
 * Created by stevendevit on 16/01/2020.
 * tankadeveloper@gmail.com
 */
class SongSearchEngine(
    private val songDataSource: ISongDataSource,
    private val songSearchTable: ISongSearchTable
) :
    ISongSearchEngine<SongSearchEngine.SongSearchRequest, SongSearchEngine.SongSearchResult> {

    override suspend fun perform(request: SongSearchRequest): SongSearchResult =
        withContext(DefaultDispatcherProvider.default()) {

            val cached = songSearchTable.getFromCache(request)

            cached?.let {

                if (it.list.size == 1)
                    return@withContext it
            }

            val query = request.query
            val musicList = songDataSource.getAllSongs()
            val resultList = mutableListOf<Music>()
            val result = SongSearchResult(request, resultList, null)
            result.generateHash()

            musicList?.let {

                val foundTracks = musicList.filter { music ->

                    return@filter music.title?.let {

                        filterSongName(it, query)
                    }!!
                }.toList()

                resultList.addAll(foundTracks)

                foundTracks.isEmpty().isTrue {

                    val parts = query.split(" ")
                    parts.forEach { part ->

                        resultList.addAll(musicList.filter { music ->

                            filterSongName(music.title!!, part)
                        }.toList())
                    }
                }
            }

            val areEqual = result.hash == cached?.hash

            if (areEqual) {

                return@withContext cached!!
            }

            songSearchTable.put(request, result)

            return@withContext result
        }

    private fun filterSongName(title: String, word: String): Boolean {

        val lowerCaseTitle = title.toLowerCase().removeDiacriticalMarks()
        val result = lowerCaseTitle.contains(word)

        if (!result) {

            return lowerCaseTitle.startsWith(word)
        }

        if (!result) {

            return lowerCaseTitle.endsWith(word)
        }

        return result
    }

    data class SongSearchResult(
        val request: SongSearchRequest,
        val list: List<Music>,
        var hash: String?
    ) {
        fun generateHash() {

            val str = list.map { it.artist + it.title }.fold(request.query + "*~*") { left, right ->
                left + right
            }

            this.hash = str.toMD5()
        }
    }

    data class SongSearchRequest(val query: String)
}

