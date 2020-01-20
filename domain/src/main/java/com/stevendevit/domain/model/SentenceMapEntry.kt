package com.stevendevit.domain.model

data class SentenceMapEntry(
    val identifier: String,
    val sentences: List<SentenceMapEntryValue>,
    var plural: Boolean = false
)