package com.stevendevit.pampa.datasource.question

import com.stevendevit.data.repository.IQuestionRepository
import com.stevendevit.domain.model.QuestionMapEntry
import com.stevendevit.domain.table.ITable
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
class QuestionTable(private val repository: IQuestionRepository) : IQuestionTable, ITable<String, QuestionMapEntry>() {

    override lateinit var table: HashMap<String, QuestionMapEntry>

    override fun load() {
        table = HashMap()
        repository.loadAllQuestions().subscribe { commands ->
            commands.forEach { entry ->
                this.table[entry.identifier] = entry
            }
        }
    }

    override fun getFromCache(key: String): QuestionMapEntry {
        return table[key]!!
    }

    override fun update(key: String, questionMapEntry: QuestionMapEntry): Single<Boolean> {

        return repository.update(key, questionMapEntry).doOnSuccess {

            table[key]?.let {
                table[key] = it.copy()
            }
        }
    }
}


interface IQuestionTable {

    fun load()
    fun getFromCache(key: String): QuestionMapEntry
    fun update(key: String, questionMapEntry: QuestionMapEntry): Single<Boolean>
}