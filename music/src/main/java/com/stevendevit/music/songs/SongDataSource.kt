package com.stevendevit.music.songs

import com.stevendevit.domain.model.Music
import com.stevendevit.music.Album
import com.stevendevit.music.paper.TableConstants
import com.stevendevit.music.songs.cache.ISongCacheRepository
import com.stevendevit.music.songs.repo.ISongRepository
import com.stevendevit.music.songs.repo.SongRepository
import com.stevendevit.shared.dispatcher.DefaultDispatcherProvider
import com.stevendevit.shared.ext.whenNotNullNorEmpty
import io.paperdb.Paper
import kotlinx.coroutines.withContext

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
class SongDataSource(
    private val songCache: ISongCacheRepository,
    private val songRepository: ISongRepository
) : ISongDataSource {

    override suspend fun getAllSongs(): MutableList<Music>? =
        withContext(DefaultDispatcherProvider.io()) {

            var allSongs = songCache.getAllSongs()

            allSongs?.whenNotNullNorEmpty{

                return@withContext it
            }

            allSongs = songRepository.getAllSongs()

            allSongs?.whenNotNullNorEmpty {
                songCache.saveAllSongs(it)
            }

            return@withContext allSongs
        }

    override suspend fun invalidateCache()  = withContext(DefaultDispatcherProvider.io()) {

        Paper.book(TableConstants.TABLE_ALL_MUSIC)
            .destroy()
    }
}