package com.stevendevit.data.model

import com.stevendevit.domain.model.SentenceMapEntryValue

data class SentenceJsonValue(
    val identifier: String, val sentences: List<SentenceMapEntryValue>
)