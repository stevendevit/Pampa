package com.stevendevit.music.playlist

import com.stevendevit.domain.model.Music
import com.stevendevit.music.PlayList
import com.stevendevit.music.paper.TableConstants
import com.stevendevit.shared.dispatcher.DefaultDispatcherProvider
import io.paperdb.Paper
import kotlinx.coroutines.withContext

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
class PlayListRepository : IPlayListRepository {

    override suspend fun getKeys(): List<String> {
        return Paper.book(TableConstants.TABLE_PLAYLIST).allKeys
    }

    override suspend fun savePlayList(playList: PlayList): Boolean =
        withContext(DefaultDispatcherProvider.io())
        {

            Paper.book(TableConstants.TABLE_PLAYLIST)
                .write(playList.identifier, playList)

            return@withContext true
        }

    override suspend fun getAllPlayList(): List<PlayList> =
        withContext(DefaultDispatcherProvider.io()) {

            val keys = getKeys()

            return@withContext keys.mapNotNull {

                Paper.book(TableConstants.TABLE_PLAYLIST)
                    .read<PlayList>(it)
            }.toList()
        }

    override suspend fun addSongToPlayList(name: String, song: Music): Boolean =
        withContext(DefaultDispatcherProvider.io()) {

            val allPlayList = getAllPlayList().firstOrNull { it.identifier == name }

            allPlayList?.let {
                it.list.toMutableList().add(song)
                return@withContext savePlayList(it)
            }

            return@withContext false
        }
}