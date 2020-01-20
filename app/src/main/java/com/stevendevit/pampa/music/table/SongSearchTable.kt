package com.stevendevit.pampa.music.table

import com.stevendevit.domain.table.ITable
import com.stevendevit.pampa.music.SongSearchEngine

/**
 * Created by stevendevit on 16/01/2020.
 * tankadeveloper@gmail.com
 */
class SongSearchTable :
    ITable<SongSearchEngine.SongSearchRequest, SongSearchEngine.SongSearchResult>(),
    ISongSearchTable {

    override val table: HashMap<SongSearchEngine.SongSearchRequest, SongSearchEngine.SongSearchResult>
            by lazy { hashMapOf<SongSearchEngine.SongSearchRequest, SongSearchEngine.SongSearchResult>() }

    override fun put(
        request: SongSearchEngine.SongSearchRequest,
        result: SongSearchEngine.SongSearchResult
    ) {
        table[request] = result
    }

    override fun getFromCache(request: SongSearchEngine.SongSearchRequest): SongSearchEngine.SongSearchResult? {

        return table[request]
    }
}

