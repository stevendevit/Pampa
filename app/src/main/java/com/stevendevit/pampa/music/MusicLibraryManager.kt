package com.stevendevit.pampa.music

import com.stevendevit.music.paper.TableConstants
import com.stevendevit.shared.dispatcher.DefaultDispatcherProvider
import io.paperdb.Paper
import kotlinx.coroutines.withContext

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
class MusicLibraryManager {

    suspend fun invalidateCache() = withContext(DefaultDispatcherProvider.io()) {

        val tables = TableConstants.Tables

        tables.forEach {

            Paper.book(it).destroy()
        }
    }
}