package com.stevendevit.pampa.module

import com.stevendevit.domain.interfaces.IPlayerCommandHandler
import com.stevendevit.domain.interfaces.ISpeech
import com.stevendevit.domain.table.ICommandTable
import com.stevendevit.domain.table.INumberTable
import com.stevendevit.domain.table.IQuestionTable
import com.stevendevit.domain.table.ISentenceTable
import com.stevendevit.music.album.IAlbumDataSource
import com.stevendevit.music.songs.ISongDataSource
import com.stevendevit.pampa.command.manager.CommandManager
import com.stevendevit.pampa.config.IPreferenceStorage
import com.stevendevit.pampa.music.*
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by stevendevit on 08/01/2020.
 * tankadeveloper@gmail.com
 */
object KoinApp : KoinComponent {

    val NumberTable: INumberTable by inject()
    val CommandTable: ICommandTable by inject()
    val QuestionTable: IQuestionTable by inject()
    val SentenceTable: ISentenceTable by inject()
    val Speech: ISpeech by inject()
    val CommandHandler: IPlayerCommandHandler by inject()
    val CommandManager: CommandManager by inject()
    val MusicPlayerInteractor: MusicPlayerInteractor by inject()

    val AlbumDataSource: IAlbumDataSource by inject()
    val SongDataSource: ISongDataSource by inject()

    val PreferenceStorage: IPreferenceStorage by inject()

    val SongSearchEngine: ISongSearchEngine<SongSearchEngine.SongSearchRequest, SongSearchEngine.SongSearchResult> by inject()
    val AlbumSearchEngine: IAlbumSearchEngine<AlbumSearchEngine.AlbumSearchRequest, AlbumSearchEngine.AlbumSearchResult> by inject()

    @JvmStatic
    fun loadTables() {

        NumberTable.load()
        CommandTable.load()
        QuestionTable.load()
        SentenceTable.load()
    }
}