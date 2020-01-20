package com.stevendevit.music

import android.app.Service
import android.content.Intent
import android.media.session.MediaSessionManager
import android.os.Binder
import android.os.IBinder

class PlayerService : Service() {

    // Binder given to clients
    private val binder = LocalBinder()

    private lateinit var mediaSession: MediaSessionManager

    // Check if is already running
    var isRunning = false

    //media player
    lateinit var mediaPlayerHolder: MediaPlayerHolder

    override fun onDestroy() {
        super.onDestroy()

        isRunning = false

        if (::mediaPlayerHolder.isInitialized) {
            //saves last played song and its position
//            if (mediaPlayerHolder.isCurrentSong) goPreferences.latestPlayedSong =
//                Pair(mediaPlayerHolder.currentSong.first, mediaPlayerHolder.playerPosition)
//
//            goPreferences.latestVolume = mediaPlayerHolder.currentVolumeInPercent

            mediaPlayerHolder.release()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /*This mode makes sense for things that will be explicitly started
        and stopped to run for arbitrary periods of time, such as a service
        performing background music playback.*/
        isRunning = true
        return START_NOT_STICKY
    }

    private fun initMediaSession(){

    }

    override fun onBind(intent: Intent): IBinder {
        if (!::mediaPlayerHolder.isInitialized) {
            mediaPlayerHolder = MediaPlayerHolder(this).apply {
                registerActionsReceiver()
            }
        }
        return binder
    }

    inner class LocalBinder : Binder() {
        // Return this instance of PlayerService so we can call public methods
        fun getService() = this@PlayerService
    }
}
