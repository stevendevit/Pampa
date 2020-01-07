package com.stevendevit.pampa

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stevendevit.data.commands.AbstractCommand
import com.stevendevit.data.interfaces.ICommandManagerDelegate
import com.stevendevit.data.interfaces.ICommandCycleDelegate
import com.stevendevit.domain.model.CommandMetaData
import com.stevendevit.pampa.command.handler.DefaultPlayerCommandHandler
import com.stevendevit.pampa.command.manager.CommandManager
import com.stevendevit.pampa.datasource.command.ICommandTable
import com.stevendevit.pampa.datasource.number.NumberTable
import com.stevendevit.pampa.datasource.question.IQuestionTable
import com.stevendevit.pampa.datasource.sentence.ISentenceTable
import com.stevendevit.pampa.voice.speech.ISpeech
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), ICommandCycleDelegate,
    ICommandManagerDelegate {

    private val numberTable: NumberTable by inject()
    private val commandTable: ICommandTable by inject()
    private val questionTable: IQuestionTable by inject()
    private val sentenceTable: ISentenceTable by inject()
    private val speech: ISpeech by inject()
    private val commandManager: CommandManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_settings
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setup()
    }

    private fun setup() {

        numberTable.load()
        commandTable.load()
        questionTable.load()
        sentenceTable.load()

        speech.init(this)

        commandManager
            .assignHandler(DefaultPlayerCommandHandler(this, commandTable))
            .assignCommandCycleDelegate(this)
            .assignManagerDelegate(this)

        val commands = listOf("volumen 10", "test 2")

        commandManager.performBatch(commands)

        println()
    }

    override fun onParamsNotValid(commandMetaData: CommandMetaData) {
    }

    override fun onCommandNotRecognized(commandMetaData: CommandMetaData) {
    }

    override fun onError(commandMetaData: CommandMetaData, throwable: Throwable) {
    }

    override fun onCommandPerformed(
        commandMetaData: CommandMetaData,
        result: AbstractCommand.AbstractCommandResult<*>
    ) {
    }

    override fun onResume() {
        super.onResume()

//        speech.say("TESTTTTTTTTTTTTT", object: ITextToSpeechCallback {
//            override fun onCompleted() {
//                super.onCompleted()
//            }
//        })
    }
}
