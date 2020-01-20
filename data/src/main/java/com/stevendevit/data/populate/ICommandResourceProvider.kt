package com.stevendevit.data.populate

import com.stevendevit.data.model.CommandJsonValue
import com.stevendevit.data.model.NumberJsonValue
import com.stevendevit.data.model.QuestionJsonValue
import com.stevendevit.data.model.SentenceJsonValue
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 13/01/2020.
 * tankadeveloper@gmail.com
 */
interface ICommandResourceProvider {

    fun provideSentences(): Single<List<SentenceJsonValue>>
    fun provideQuestions(): Single<List<QuestionJsonValue>>
    fun provideNumbers(): Single<List<NumberJsonValue>>
    fun provideCommands(): Single<List<CommandJsonValue>>
}