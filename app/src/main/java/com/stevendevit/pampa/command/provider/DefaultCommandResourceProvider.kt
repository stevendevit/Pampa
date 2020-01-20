package com.stevendevit.pampa.command.provider

import android.content.Context
import com.google.gson.GsonBuilder
import com.stevendevit.data.model.*
import com.stevendevit.data.populate.ICommandResourceProvider
import com.stevendevit.pampa.R
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 13/01/2020.
 * tankadeveloper@gmail.com
 */
class DefaultCommandResourceProvider(private val context: Context) : ICommandResourceProvider {

    private val gsonBuilder by lazy { GsonBuilder().create() }

    override fun provideSentences(): Single<List<SentenceJsonValue>> {

        return Single.just(this).flatMap {

            val sentencesJsonResource =
                context.resources.openRawResource(R.raw.sentences).bufferedReader().use {
                    it.readText()
                }

            val sentencesJson =
                gsonBuilder.fromJson(sentencesJsonResource, SentenceJsonEntry::class.java)

            return@flatMap Single.just(sentencesJson.sentences!!)
        }
    }

    override fun provideQuestions(): Single<List<QuestionJsonValue>> {

        return Single.just(this).flatMap {

            val questionsJsonResource =
                context.resources.openRawResource(R.raw.questions).bufferedReader().use {
                    it.readText()
                }

            val questionsJson =
                gsonBuilder.fromJson(questionsJsonResource, QuestionJsonEntry::class.java)

            return@flatMap Single.just(questionsJson.questions!!)
        }
    }

    override fun provideNumbers(): Single<List<NumberJsonValue>> {
        return Single.just(this).flatMap {

            val numbersJsonResource =
                context.resources.openRawResource(R.raw.numbers).bufferedReader().use {
                    it.readText()
                }

            val numbersJson =
                gsonBuilder.fromJson(numbersJsonResource, NumberJsonEntry::class.java)

            return@flatMap Single.just(numbersJson.numbers!!)
        }
    }

    override fun provideCommands(): Single<List<CommandJsonValue>> {

        return Single.just(this).flatMap {

            val commandsJsonResource =
                context.resources.openRawResource(R.raw.commands).bufferedReader().use {
                    it.readText()
                }

            val commandsJson =
                gsonBuilder.fromJson(commandsJsonResource, CommandJsonEntry::class.java)

            return@flatMap Single.just(commandsJson.commands!!)
        }
    }
}