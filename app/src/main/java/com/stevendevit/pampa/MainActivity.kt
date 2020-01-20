package com.stevendevit.pampa

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.stevendevit.data.constants.CommandsConstants
import com.stevendevit.domain.commands.AbstractCommand
import com.stevendevit.domain.interfaces.ICommandCycleDelegate
import com.stevendevit.domain.interfaces.ICommandManagerDelegate
import com.stevendevit.domain.interfaces.ISpeechListener
import com.stevendevit.domain.model.CommandMetaData
import com.stevendevit.domain.model.SpeechResult
import com.stevendevit.music.songs.ISongDataSource
import com.stevendevit.pampa.behavior.SearchTrackBehaviour
import com.stevendevit.pampa.command.commands.SearchTrackCommand
import com.stevendevit.pampa.module.KoinApp
import com.stevendevit.pampa.music.MusicPlayerInteractor
import com.stevendevit.pampa.view.CommandDebugView
import com.stevendevit.pampa.view.CommandGuiView
import com.stevendevit.shared.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity(), ICommandCycleDelegate,
    ICommandManagerDelegate, MusicPlayerInteractor.MusicPlayerInteractorDelegate, ISpeechListener {

    override val layoutResId: Int
        get() = R.layout.activity_main

    private val musicPlayerInteractor = KoinApp.MusicPlayerInteractor
    private val songDataSource: ISongDataSource by inject()

    private lateinit var commandGuiView: CommandGuiView
    private lateinit var debugGuiView: CommandDebugView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setup()
        setupViews()
    }

    private fun setupViews() {
        debugGuiView = debugGui
        debugGuiView.apply {
            visibility = View.VISIBLE
        }

        commandGuiView = commandGui
        commandGuiView.apply {
            visibility = View.VISIBLE
        }
        commandGuiView.setDelegate(this)

    }

    override fun onSpeechResult(result: SpeechResult) {

        commandGuiView.stopListeningForCommand()

        val data = result as SpeechResult.Data

        data.let {

            KoinApp.CommandManager.perform(it.text, true)
        }
    }

    fun onClickText(view: View) {

//        val text = txtField.text.toString()
//
//        GlobalScope.launch {
//
//            //     val result = songSearchEngine.perform(SongSearchEngine.SongSearchRequest(text))
//
//            try {
//
//                val allAlbums = KoinApp.AlbumDataSource.getAllAlbums(true)
//                val artists = KoinApp.AlbumDataSource.getAllAlbumsByArtist(text)
//
//                val albumResult =
//                    albumSearchEngine.perform(AlbumSearchEngine.AlbumSearchRequest(text))
//
//                println()
//
//            } catch (ex: Exception) {
//
//                println()
//            }
//        }
    }

    override fun onViewCanObserve() {
    }

    override fun onBackPressed() {

        commandGuiView.hide()
        debugGuiView.hide()

        if (!commandGuiView.canGoBack() || !debugGuiView.canGoBack()) {
            return
        }

        super.onBackPressed()
    }

    override fun onServiceBinded() {

    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (KeyEvent.KEYCODE_VOLUME_UP == keyCode) {
   //         commandGuiView.startListeningForCommand()
        }

        return super.onKeyUp(keyCode, event)
    }

    override fun onServiceDisconnected() {
    }

    private fun setup() {
        musicPlayerInteractor.init(this)
        musicPlayerInteractor.delegate = this
        musicPlayerInteractor.startService()
        KoinApp.Speech.init(this)

        KoinApp.CommandManager
            .assignHandler(KoinApp.CommandHandler)
            .assignCommandCycleDelegate(this)
            .assignManagerDelegate(this)
    }

    override fun onParamsNotValid(commandMetaData: CommandMetaData) {
    }

    override fun onCommandNotRecognized(commandMetaData: CommandMetaData) {
    }

    override fun onError(commandMetaData: CommandMetaData, throwable: Throwable) {
    }

    override fun onCommandPerformed(
        commandMetaData: CommandMetaData,
        result: AbstractCommand.Result<*>
    ) {
        when {
            commandMetaData.identifier == KoinApp.CommandTable.getFromCache(CommandsConstants.CommandGroup.COMMAND_ID_SEARCH_TRACK).localisedIdentifier
            -> {
                val searchTrackResult =
                    result as SearchTrackCommand.SearchTrackCommandResult.Success

                SearchTrackBehaviour(
                    searchTrackResult,
                    KoinApp.MusicPlayerInteractor,
                    KoinApp.Speech,
                    KoinApp.SentenceTable,
                    KoinApp.QuestionTable,
                    KoinApp.NumberTable
                ).perform()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        musicPlayerInteractor.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPlayerInteractor.onDestroy()
    }
}
