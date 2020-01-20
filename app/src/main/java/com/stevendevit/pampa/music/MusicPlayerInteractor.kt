package com.stevendevit.pampa.music

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.stevendevit.domain.model.Music
import com.stevendevit.music.MediaPlayerHolder
import com.stevendevit.music.MediaPlayerInterface
import com.stevendevit.music.PlayerService
import java.lang.ref.WeakReference

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
class MusicPlayerInteractor {

    private lateinit var weakContext: WeakReference<Context>
    private val thisContext get() = weakContext.get()
    private var serviceBound = false

    private lateinit var mPlayerService: PlayerService
    private lateinit var mBindingIntent: Intent
    private val isMediaPlayerHolder get() = ::mMediaPlayerHolder.isInitialized

    lateinit var mMediaPlayerHolder: MediaPlayerHolder
    lateinit var delegate: MusicPlayerInteractorDelegate

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName, service: IBinder) {
            val binder = service as PlayerService.LocalBinder
            mPlayerService = binder.getService()
            serviceBound = true
            mMediaPlayerHolder = mPlayerService.mediaPlayerHolder
            mMediaPlayerHolder.mediaPlayerInterface = mMediaPlayerInterface
            delegate.onServiceBinded()
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            serviceBound = false
            delegate.onServiceDisconnected()
        }
    }

    fun init(context: Context) {

        this.weakContext = WeakReference(context)
    }

    fun startService() {
        bindService()
    }

    fun onResume() {
        if (isMediaPlayerHolder && mMediaPlayerHolder.isMediaPlayer) mMediaPlayerHolder.onResumeActivity()
    }

    private fun bindService() {

        thisContext?.let {
            val intent = Intent(it, PlayerService::class.java)
            it.bindService(intent, connection, Context.BIND_AUTO_CREATE)
            mBindingIntent = intent
        }
    }

    fun onDestroy() {

        thisContext?.let {
            if (serviceBound) it.unbindService(connection)
            mPlayerService.stopForeground(true)
            it.stopService(mBindingIntent)
        }
    }

    fun playNow(music: Music, list: List<Music>, queue : Boolean = false) {

        mMediaPlayerHolder.setCurrentSong(music, list, queue)
        mMediaPlayerHolder.initMediaPlayer(music)
    }

    private val mMediaPlayerInterface = object : MediaPlayerInterface {

        override fun onPlaybackCompleted() {
        }

        override fun onUpdateRepeatStatus() {
        }

        override fun onClose() {
        }

        override fun onPositionChanged(position: Int) {
        }

        override fun onStateChanged() {
        }

        override fun onQueueEnabled() {
        }

        override fun onQueueCleared() {
        }

        override fun onQueueStartedOrEnded(started: Boolean) {

        }
    }

    interface MusicPlayerInteractorDelegate {

        fun onServiceBinded()
        fun onServiceDisconnected()
    }
}