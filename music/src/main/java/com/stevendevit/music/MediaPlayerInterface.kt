package com.stevendevit.music

interface MediaPlayerInterface {
    fun onPositionChanged(position: Int)
    fun onStateChanged()
    fun onPlaybackCompleted()
    fun onClose()
    fun onUpdateRepeatStatus()
    fun onQueueEnabled()
    fun onQueueCleared()
    fun onQueueStartedOrEnded(started: Boolean)
}
