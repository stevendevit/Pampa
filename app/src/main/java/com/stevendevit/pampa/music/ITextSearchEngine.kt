package com.stevendevit.pampa.music

/**
 * Created by stevendevit on 16/01/2020.
 * tankadeveloper@gmail.com
 */
interface ITextSearchEngine<REQUEST, RESULT> {

    suspend fun perform(request: REQUEST): RESULT
}