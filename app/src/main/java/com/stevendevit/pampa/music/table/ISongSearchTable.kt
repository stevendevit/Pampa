package com.stevendevit.pampa.music.table

import com.stevendevit.pampa.music.SongSearchEngine

interface ISongSearchTable {

    fun put(request: SongSearchEngine.SongSearchRequest, result: SongSearchEngine.SongSearchResult)
    fun getFromCache(request: SongSearchEngine.SongSearchRequest): SongSearchEngine.SongSearchResult?
}