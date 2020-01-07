package com.stevendevit.data.populate

import com.stevendevit.data.model.CommandJsonValue
import com.stevendevit.data.model.NumberJsonValue
import com.stevendevit.data.model.QuestionJsonValue
import com.stevendevit.data.model.SentenceJsonValue
import com.stevendevit.domain.model.NumberMapEntry
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 05/01/2020.
 * tankadeveloper@gmail.com
 */
interface ICommandPopulate {

    fun populateAllDefaultCommands(commands : List<CommandJsonValue>) : Single<Boolean>
    fun populateAllDefaultSentences(sentences : List<SentenceJsonValue>) : Single<Boolean>
    fun populateAllDefaultNumbers(numbers : List<NumberJsonValue>) : Single<Boolean>
    fun populateAllQuestions(questions : List<QuestionJsonValue>) : Single<Boolean>
}