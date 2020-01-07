package com.stevendevit.pampa.model

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
sealed class SpeechResult{

    class Data(val text: String): SpeechResult()
}