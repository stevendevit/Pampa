package com.stevendevit.pampa

import android.app.Application
import com.stevendevit.data.model.CommandJsonValue
import com.stevendevit.data.model.NumberJsonValue
import com.stevendevit.data.model.QuestionJsonValue
import com.stevendevit.data.model.SentenceJsonValue
import com.stevendevit.data.populate.CommandResourcePopulateImpl
import com.stevendevit.domain.model.Music
import com.stevendevit.music.paper.TableConstants
import com.stevendevit.pampa.command.provider.DefaultCommandResourceProvider
import com.stevendevit.pampa.module.KoinApp
import com.stevendevit.pampa.module.KoinModules
import com.stevendevit.shared.scheduler.defaultIoMain
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function4
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import java.util.concurrent.TimeUnit


/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */

class PampaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
        Paper.book().destroy()
        startKoin()
        invalidateCache()
        populateData()
    }

    private fun startKoin() {

        org.koin.core.context.startKoin {
            androidContext(this@PampaApp)
            androidLogger()
            modules(KoinModules.modules)
        }
    }

    private fun invalidateCache(){

        runBlocking {
            KoinApp.AlbumDataSource.invalidateCache()
            KoinApp.SongDataSource.invalidateCache()
        }

        GlobalScope.launch {

        }
    }

    private fun populateData() {

        val commandResourceProvider =
            DefaultCommandResourceProvider(this)

        val commands = commandResourceProvider.provideCommands()
        val sentences = commandResourceProvider.provideSentences()
        val questions = commandResourceProvider.provideQuestions()
        val numbers = commandResourceProvider.provideNumbers()

        Single.zip<List<CommandJsonValue>, List<SentenceJsonValue>, List<QuestionJsonValue>, List<NumberJsonValue>, Unit>(
            commands, sentences, questions, numbers,
            Function4 { t1, t2, t3, t4 ->

                val dataPopulator = CommandResourcePopulateImpl()

                Single.just(0).delay(0, TimeUnit.SECONDS).subscribe { it ->

                    Single.zip<Boolean, Boolean, Boolean, Boolean, Unit>(
                        dataPopulator.populateAllDefaultCommands(t1),
                        dataPopulator.populateAllDefaultSentences(t2),
                        dataPopulator.populateAllQuestions(t3),
                        dataPopulator.populateAllDefaultNumbers(t4),
                        Function4 { r1, r2, r3, r4 ->

                            listOf(r1, r2, r3, r4).none { it == false }

                            println("load tables")

                            KoinApp.loadTables()
                        })
                        .defaultIoMain()
                        .subscribe({

                        }, {

                            println()
                        })
                }
            })
            .defaultIoMain()
            .subscribe()
    }
}