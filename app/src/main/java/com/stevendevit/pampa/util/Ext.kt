package com.stevendevit.pampa.util

import java.text.Normalizer

/**
 * Created by stevendevit on 17/01/2020.
 * tankadeveloper@gmail.com
 */

fun String.removeDiacriticalMarks(): String {
    var s = Normalizer.normalize(this, Normalizer.Form.NFD)
    s = s.replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
    return s
}