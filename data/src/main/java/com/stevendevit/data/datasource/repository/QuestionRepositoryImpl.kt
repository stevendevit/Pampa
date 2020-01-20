package com.stevendevit.data.datasource.repository

import com.stevendevit.data.constants.PaperConstants
import com.stevendevit.domain.repository.IQuestionRepository
import com.stevendevit.domain.model.QuestionMapEntry
import com.stevendevit.shared.scheduler.defaultIoMain
import io.paperdb.Paper
import io.reactivex.rxjava3.core.Single

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
class QuestionRepositoryImpl : IQuestionRepository {

    override fun loadAllQuestions(): Single<List<QuestionMapEntry>> {
        return Single.just(Paper.book().read<List<QuestionMapEntry>>(PaperConstants.TABLE_QUESTIONS))
    }

    override fun update(identifier: String, entry: QuestionMapEntry): Single<Boolean> {
        return Single.just(loadAllQuestions()).flatMap<Boolean> { list ->

            val newItems =
                list.blockingGet().dropWhile { it.identifier == identifier }.plus(entry.copy())
            Paper.book().write(identifier, newItems)
            Single.just(false)
        }.defaultIoMain().doOnError {
            Single.error<Boolean>(it)
        }
    }
}