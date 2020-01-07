package com.stevendevit.pampa

import android.app.Application
import com.google.gson.GsonBuilder
import com.stevendevit.data.constants.PaperConstants
import com.stevendevit.data.model.CommandJsonEntry
import com.stevendevit.data.model.NumberJsonEntry
import com.stevendevit.data.model.QuestionJsonEntry
import com.stevendevit.data.model.SentenceJsonEntry
import com.stevendevit.data.populate.CommandPopulateImpl
import com.stevendevit.domain.model.CommandMapEntry
import com.stevendevit.domain.model.NumberMapEntry
import com.stevendevit.pampa.datasource.command.CommandTable
import com.stevendevit.pampa.datasource.repository.CommandRepositoryImpl
import com.stevendevit.pampa.module.KoinModules
import io.paperdb.Paper
import org.koin.android.ext.koin.androidContext

/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */

class PampaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)

        CommandTable(CommandRepositoryImpl())

        populateData()

        startKoin()
    }

    private fun startKoin() {

        org.koin.core.context.startKoin {
            androidContext(this@PampaApp)
            modules(KoinModules.NumbersTableModule)
            modules(KoinModules.CommandsTableModule)
            modules(KoinModules.SpeechModule)
            modules(KoinModules.QuestionModule)
            modules(KoinModules.SentencesModule)
        }
    }

    private fun populateData() {

        val gsonBuilder = GsonBuilder().create()

        val commandsJsonResource = resources.openRawResource(R.raw.commands).bufferedReader().use {
            it.readText()
        }

        val numbersJsonResource = resources.openRawResource(R.raw.numbers).bufferedReader().use {
            it.readText()
        }

        val questionsJsonResource= resources.openRawResource(R.raw.questions).bufferedReader().use {
            it.readText()
        }

        val sentencesJsonResource= resources.openRawResource(R.raw.sentences).bufferedReader().use {
            it.readText()
        }

        val commandsJson =
            gsonBuilder.fromJson(commandsJsonResource, CommandJsonEntry::class.java)

        val numbersJson =
            gsonBuilder.fromJson(numbersJsonResource, NumberJsonEntry::class.java)

        val questionsJson =
            gsonBuilder.fromJson(questionsJsonResource, QuestionJsonEntry::class.java)

        val sentencesJson =
            gsonBuilder.fromJson(sentencesJsonResource, SentenceJsonEntry::class.java)

        val dataPopulator = CommandPopulateImpl()
        CommandPopulateImpl()
            .populateAllDefaultCommands(commandsJson.commands!!)
            .mergeWith(dataPopulator.populateAllDefaultNumbers(numbersJson.numbers!!))
            .mergeWith(dataPopulator.populateAllQuestions(questionsJson.questions!!))
            .mergeWith(dataPopulator.populateAllDefaultSentences(sentencesJson.sentences!!))
            .subscribe {

              /*  val commands =
                    Paper.book().read<List<CommandMapEntry>>(PaperConstants.TABLE_COMMANDS)
                val numbers =
                    Paper.book().read<List<NumberMapEntry>>(PaperConstants.TABLE_NUMBERS)*/

                println("Succeeded table dumps")
            }
    }
}