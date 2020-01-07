package com.stevendevit.pampa.module

import com.stevendevit.data.repository.ICommandRepository
import com.stevendevit.data.repository.INumberRepository
import com.stevendevit.data.repository.IQuestionRepository
import com.stevendevit.data.repository.ISentenceRepository
import com.stevendevit.pampa.command.manager.CommandManager
import com.stevendevit.pampa.datasource.command.CommandTable
import com.stevendevit.pampa.datasource.command.ICommandTable
import com.stevendevit.pampa.datasource.number.NumberTable
import com.stevendevit.pampa.datasource.question.IQuestionTable
import com.stevendevit.pampa.datasource.question.QuestionTable
import com.stevendevit.pampa.datasource.repository.CommandRepositoryImpl
import com.stevendevit.pampa.datasource.repository.NumberRepositoryImpl
import com.stevendevit.pampa.datasource.repository.QuestionRepositoryImpl
import com.stevendevit.pampa.datasource.repository.SentenceRepositoryImpl
import com.stevendevit.pampa.datasource.sentence.ISentenceTable
import com.stevendevit.pampa.datasource.sentence.SentenceTable
import com.stevendevit.pampa.voice.speech.ISpeech
import com.stevendevit.pampa.voice.speech.SpeechImpl
import org.koin.dsl.module

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */

object KoinModules {

    private val NumbersTableModule = module {
        single { NumberTable(get()) }
        single<INumberRepository> { NumberRepositoryImpl() }
    }

    private val CommandsTableModule = module {
        single { CommandManager(get()) }
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

    val AllModules = listOf(
        NumbersTableModule,
        CommandsTableModule,
        QuestionModule,
        SentencesModule,
        SpeechModule
    )
}
