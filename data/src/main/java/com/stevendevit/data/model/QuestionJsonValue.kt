package com.stevendevit.data.model

import com.stevendevit.domain.model.QuestionMapEntryValue

data class QuestionJsonValue(
    val identifier: String, val questions: List<QuestionMapEntryValue>
)

