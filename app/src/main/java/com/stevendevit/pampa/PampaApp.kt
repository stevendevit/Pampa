package com.stevendevit.pampa

import android.app.Application
import com.google.gson.GsonBuilder
import com.stevendevit.data.model.CommandJsonEntry
import com.stevendevit.data.model.NumberJsonEntry
import com.stevendevit.data.model.QuestionJsonEntry
import com.stevendevit.data.model.SentenceJsonEntry
import com.stevendevit.data.populate.CommandPopulateImpl
import com.stevendevit.pampa.module.KoinModules
import io.paperdb.Paper
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */

class PampaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
        startKoin()

        // TODO do it only for the first time
        populateData()
    }

    private fun startKoin() {

        org.koin.core.context.startKoin {
            androidContext(this@PampaApp)
            androidLogger()
            modules(KoinModules.AllModules)
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

        val questionsJsonResource =
            resources.openRawResource(R.raw.questions).bufferedReader().use {
                it.readText()
            }

        val sentencesJsonResource =
            resources.openRawResource(R.raw.sentences).bufferedReader().use {
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

             /*   val commands =
                    Paper.book().read<List<CommandMapEntry>>(PaperConstants.TABLE_COMMANDS)
                val questionsn =
                    Paper.book().read<List<QuestionJsonEntry>>(PaperConstants.TABLE_QUESTIONS)

                println("Succeeded tables dump")*/
            }
    }
}