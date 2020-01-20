package com.stevendevit.data.datasource.question

import com.stevendevit.domain.repository.IQuestionRepository
import com.stevendevit.domain.model.QuestionMapEntry
import com.stevendevit.domain.table.IQuestionTable
import com.stevendevit.domain.table.ITable
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
class QuestionTable(private val repository: IQuestionRepository) : IQuestionTable,
    ITable<String, QuestionMapEntry>() {

    override val table: HashMap<String, QuestionMapEntry> by lazy { hashMapOf<String, QuestionMapEntry>() }

    override fun load() {
        repository.loadAllQuestions().subscribe { commands ->
            commands.forEach { entry ->
                this.table[entry.identifier] = entry
            }
        }
    }

    override fun getFromCache(group: String): QuestionMapEntry {
        return table[group]!!
    }

    override fun getQuestion(group: String, identifier: String): String {
        val questionGroup = getFromCache(group)
        return questionGroup.questions.first { it.identifier == identifier }.localised
    }

    override fun update(key: String, questionMapEntry: QuestionMapEntry): Single<Boolean> {

        return repository.update(key, questionMapEntry).doOnSuccess {

            table[key]?.let {
                table[key] = it.copy()
            }
        }
    }
}

