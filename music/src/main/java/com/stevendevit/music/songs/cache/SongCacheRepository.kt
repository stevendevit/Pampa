package com.stevendevit.music.songs.cache

import com.stevendevit.domain.model.Music
import com.stevendevit.music.paper.TableConstants
import com.stevendevit.shared.dispatcher.DefaultDispatcherProvider
import com.stevendevit.shared.ext.whenNotNullNorEmpty
import io.paperdb.Paper
import kotlinx.coroutines.withContext

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
class SongCacheRepository : ISongCacheRepository {
    override suspend fun saveAllSongs(musicList: MutableList<Music>): Boolean =
        withContext(DefaultDispatcherProvider.io()) {
            Paper.book(TableConstants.TABLE_ALL_MUSIC)
                .write(TableConstants.TABLE_ALL_MUSIC_KEY, musicList)
            return@withContext true
        }

    override suspend fun addNewSong(music: Music) =
        withContext(DefaultDispatcherProvider.io()) {

            val songs = getAllSongs()

            songs?.whenNotNullNorEmpty {
                songs.add(music)
                return@withContext saveAllSongs(songs)
            }

            return@withContext false
        }

    override suspend fun getAllSongs(): MutableList<Music>?  =
        withContext(DefaultDispatcherProvider.io()) {

            return@withContext Paper.book(TableConstants.TABLE_ALL_MUSIC)
                .read<MutableList<Music>>(TableConstants.TABLE_ALL_MUSIC_KEY)
        }
}