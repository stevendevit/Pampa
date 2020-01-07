package com.stevendevit.pampa.voice.speech

import com.stevendevit.pampa.model.SpeechResult

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
interface ISpeechListener {

    fun onStartOfSpeech() {}

    fun onSpeechPartialResults(results: MutableList<String>?) {
    }

    fun onSpeechRmsChanged(value: Float) {
    }

    fun onSpeechResult(result: SpeechResult)
}