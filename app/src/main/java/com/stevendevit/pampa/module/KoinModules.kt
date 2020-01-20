package com.stevendevit.pampa.module

import com.stevendevit.data.datasource.command.CommandTable
import com.stevendevit.data.datasource.number.NumberTable
import com.stevendevit.data.datasource.question.QuestionTable
import com.stevendevit.data.datasource.repository.CommandRepositoryImpl
import com.stevendevit.data.datasource.repository.NumberRepositoryImpl
import com.stevendevit.data.datasource.repository.QuestionRepositoryImpl
import com.stevendevit.data.datasource.repository.SentenceRepositoryImpl
import com.stevendevit.data.datasource.sentence.SentenceTable
import com.stevendevit.domain.interfaces.IPlayerCommandHandler
import com.stevendevit.domain.interfaces.ISpeech
import com.stevendevit.domain.repository.ICommandRepository
import com.stevendevit.domain.repository.INumberRepository
import com.stevendevit.domain.repository.IQuestionRepository
import com.stevendevit.domain.repository.ISentenceRepository
import com.stevendevit.domain.table.ICommandTable
import com.stevendevit.domain.table.INumberTable
import com.stevendevit.domain.table.IQuestionTable
import com.stevendevit.domain.table.ISentenceTable
import com.stevendevit.music.album.AlbumDataSource
import com.stevendevit.music.album.IAlbumDataSource
import com.stevendevit.music.album.cache.AlbumCacheRepository
import com.stevendevit.music.album.cache.IAlbumCacheRepository
import com.stevendevit.music.album.repo.AlbumRepository
import com.stevendevit.music.album.repo.IAlbumRepository
import com.stevendevit.music.playlist.IPlayListRepository
import com.stevendevit.music.playlist.PlayListRepository
import com.stevendevit.music.songs.ISongDataSource
import com.stevendevit.music.songs.SongDataSource
import com.stevendevit.music.songs.cache.ISongCacheRepository
import com.stevendevit.music.songs.cache.SongCacheRepository
import com.stevendevit.music.songs.repo.ISongRepository
import com.stevendevit.music.songs.repo.SongRepository
import com.stevendevit.pampa.command.handler.DefaultPlayerCommandHandler
import com.stevendevit.pampa.command.manager.CommandManager
import com.stevendevit.pampa.config.IPreferenceStorage
import com.stevendevit.pampa.config.PreferenceStorage
import com.stevendevit.pampa.music.*
import com.stevendevit.pampa.music.table.ISongSearchTable
import com.stevendevit.pampa.music.table.SongSearchTable
import com.stevendevit.pampa.voice.speech.SpeechImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.definition.Kind
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */

object KoinModules : IKModuleProvider {

    override val modules: List<Module>
        get() = AllModules

    private val NumbersTableModule = module {
        single<INumberTable> { NumberTable(get()) }
        single<INumberRepository> { NumberRepositoryImpl() }
    }

    private val RepositoriesModule = module {

        single<IPlayListRepository> { PlayListRepository() }

        single<ISongCacheRepository> { SongCacheRepository() }
        single<ISongRepository> { SongRepository(androidContext().contentResolver) }
        single<ISongDataSource> { SongDataSource(get(), get()) }

        single<IAlbumCacheRepository> { AlbumCacheRepository() }
        single<IAlbumRepository> { AlbumRepository(get()) }
        single<IAlbumDataSource> { AlbumDataSource(get(), get()) }
    }

    private val MusicPlayerInteractor = module {
        single { MusicPlayerInteractor() }
    }

    private val ViewModels = module {
        viewModel {
            CommandManager(get())
        }
    }

    private val CommandsTableModule = module {
        single<ICommandTable> { CommandTable(get()) }
        single<ICommandRepository> { CommandRepositoryImpl() }
    }

    private val QuestionModule = module {
        single<IQuestionTable> { QuestionTable(get()) }
        single<IQuestionRepository> { QuestionRepositoryImpl() }
    }

    private val SentencesModule = module {
        single<ISentenceTable> { SentenceTable(get()) }
        single<ISentenceRepository> { SentenceRepositoryImpl() }
    }

    private val SpeechModule = module {
        single<ISpeech> { SpeechImpl() }
    }

    private val EngineTableModule = module {
        single<ISongSearchTable> { SongSearchTable() }
    }

    private val CommandHandlerModule = module {
        single<IPlayerCommandHandler> { DefaultPlayerCommandHandler(androidContext(), get()) }
    }

    private val SearchEngineModule = module {
        single<ISongSearchEngine<SongSearchEngine.SongSearchRequest, SongSearchEngine.SongSearchResult>> {
            SongSearchEngine(
                get(),
                get()
            )
        }
        single<IAlbumSearchEngine<AlbumSearchEngine.AlbumSearchRequest, AlbumSearchEngine.AlbumSearchResult>> {
            AlbumSearchEngine(
                get()
            )
        }
    }

    private val PreferenceModule = module {
        single<IPreferenceStorage> { PreferenceStorage(androidContext()) }
    }

    private val AllModules = listOf(
        NumbersTableModule,
        CommandsTableModule,
        QuestionModule,
        SentencesModule,
        SpeechModule,
        CommandHandlerModule,
        MusicPlayerInteractor,
        RepositoriesModule,
        SearchEngineModule,
        ViewModels,
        EngineTableModule,
        PreferenceModule
    )
}
