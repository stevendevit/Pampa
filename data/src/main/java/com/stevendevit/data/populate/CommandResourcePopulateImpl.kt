package com.stevendevit.data.populate

import com.stevendevit.data.constants.PaperConstants
import com.stevendevit.data.model.CommandJsonValue
import com.stevendevit.data.model.NumberJsonValue
import com.stevendevit.data.model.QuestionJsonValue
import com.stevendevit.data.model.SentenceJsonValue
import com.stevendevit.domain.model.CommandMapEntry
import com.stevendevit.domain.model.NumberMapEntry
import com.stevendevit.domain.model.QuestionMapEntry
import com.stevendevit.domain.model.SentenceMapEntry
import io.paperdb.Paper
import io.paperdb.PaperDbException
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */
class CommandResourcePopulateImpl : ICommandResourcePopulate {
    override fun populateAllDefaultSentences(sentences: List<SentenceJsonValue>): Single<Boolean> {
        val list = sentences.map {
            SentenceMapEntry(it.identifier, it.sentences)
        }

        return try {
            Paper.book().write(PaperConstants.TABLE_SENTENCES, list)
            Single.just(true)
        } catch (ex : PaperDbException){
            Single.just(false)
        }
    }

    override fun populateAllQuestions(questions: List<QuestionJsonValue>): Single<Boolean> {

        val list = questions.map {
            QuestionMapEntry(it.identifier, it.questions)
        }

        return try {

            Paper.book().write(PaperConstants.TABLE_QUESTIONS, list)
            Single.just(true)
        } catch (ex : PaperDbException){
            Single.just(false)
        }
    }

    override fun populateAllDefaultNumbers(numbers: List<NumberJsonValue>): Single<Boolean> {
        val list = numbers.map {
            NumberMapEntry(it.identifier, it.localised, it.value)
        }

        return try {

            Paper.book().write(PaperConstants.TABLE_NUMBERS, list)
            Single.just(true)
        } catch (ex : PaperDbException){
            Single.just(false)
        }
    }

    override fun populateAllDefaultCommands(commands: List<CommandJsonValue>): Single<Boolean> {

        val list = commands.map {
            CommandMapEntry(it.command, it.localised, it.subCommands, it.parameters)
        }

        return try {

            Paper.book().write(PaperConstants.TABLE_COMMANDS, list)
            Single.just(true)
        } catch (ex : PaperDbException){
            Single.just(false)
        }
    }
}