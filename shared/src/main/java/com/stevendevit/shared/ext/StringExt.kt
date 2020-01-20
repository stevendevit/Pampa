package com.stevendevit.shared.ext

import java.security.MessageDigest

/**
 * Created by stevendevit on 16/01/2020.
 * tankadeveloper@gmail.com
 */

fun String.toMD5(): String {
    val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
    return bytes.toHex()
}

fun ByteArray.toHex(): String {
    return joinToString("") { "%02x".format(it) }
}