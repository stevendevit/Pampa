package com.stevendevit.domain.table

import com.stevendevit.domain.model.QuestionMapEntry
import io.reactivex.rxjava3.core.Single

interface IQuestionTable {

    fun load()
    fun getFromCache(group: String): QuestionMapEntry
    fun getQuestion(group: String, identifier : String) : String
    fun update(key: String, questionMapEntry: QuestionMapEntry): Single<Boolean>
}