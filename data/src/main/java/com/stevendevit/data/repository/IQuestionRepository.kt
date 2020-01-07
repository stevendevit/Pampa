package com.stevendevit.data.repository

import com.stevendevit.domain.model.QuestionMapEntry
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
interface IQuestionRepository {

    fun loadAllQuestions(): Single<List<QuestionMapEntry>>
    fun update(identifier: String, entry: QuestionMapEntry): Single<Boolean>
}