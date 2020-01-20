package com.stevendevit.music

/**
 * Created by stevendevit on 13/01/2020.
 * tankadeveloper@gmail.com
 */
interface IMusicPlayerDelegate {

    fun repeatSong(now : Boolean = false)
    fun addToQueue()
}