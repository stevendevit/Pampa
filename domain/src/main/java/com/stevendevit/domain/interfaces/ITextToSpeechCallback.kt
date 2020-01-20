package com.stevendevit.domain.interfaces

interface ITextToSpeechCallback {
    fun onError() {}

    fun onStart() {

    }

    fun onCompleted() {}
}