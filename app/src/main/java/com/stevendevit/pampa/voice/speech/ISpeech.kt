package com.stevendevit.pampa.voice.speech

import android.content.Context

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
interface ISpeech {

    fun init(context: Context)
    fun say(string: String, listener: ITextToSpeechCallback)
    fun stopSpeech()
    fun startListening(listener: ISpeechListener)
    fun stopListening()
}