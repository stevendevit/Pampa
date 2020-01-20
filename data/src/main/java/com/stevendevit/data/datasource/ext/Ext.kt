package com.stevendevit.data.datasource.ext

/**
 * Created by stevendevit on 18/01/2020.
 * tankadeveloper@gmail.com
 */

//"localised": "He encontrado %s [canción|canciones]",

fun String.getSingularOrPlural(count: Int): String {

    val splitted = this.split("|")
    val sentence = splitted.getOrElse(0) { "" }.format(count)
    val toReplaceMatrix = splitted.getOrElse(1) { "" }.split(":")
    val singularValue = toReplaceMatrix.getOrElse(0) { "" }
    val pluralValue = toReplaceMatrix.getOrElse(1) { "" }

    return sentence.plus(if (count > 1) pluralValue else singularValue)
}