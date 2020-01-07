package com.stevendevit.pampa.voice.speech

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
interface ITextToSpeechCallback {
    fun onError() {}

    fun onStart() {

    }

    fun onCompleted() {}
}