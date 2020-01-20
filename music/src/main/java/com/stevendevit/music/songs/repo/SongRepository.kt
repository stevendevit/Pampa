package com.stevendevit.music.songs.repo

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.MediaStore
import com.stevendevit.domain.model.Music
import com.stevendevit.music.MusicUtils
import com.stevendevit.shared.dispatcher.DefaultDispatcherProvider
import kotlinx.coroutines.withContext

/**
 * Created by stevendevit on 14/01/2020.
 * tankadeveloper@gmail.com
 */
class SongRepository(private val contentResolver: ContentResolver) :
    ISongRepository {

    @Suppress("DEPRECATION")
    @SuppressLint("InlinedApi")
    override suspend fun getAllSongs(): MutableList<Music> =

        withContext(DefaultDispatcherProvider.io()) {
            val musicCursor = MusicUtils.getMusicCursor(contentResolver)
            val songs = mutableListOf<Music>()

            try {

                musicCursor?.use {
                    if (it.moveToFirst()) {

                        val artist =
                            it.getColumnIndex(MediaStore.Audio.AudioColumns.ARTIST)
                        val year =
                            it.getColumnIndex(MediaStore.Audio.AudioColumns.YEAR)
                        val track =
                            it.getColumnIndex(MediaStore.Audio.AudioColumns.TRACK)
                        val title =
                            it.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE)
                        val duration =
                            it.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION)
                        val album =
                            it.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM)
                        val path =
                            it.getColumnIndex(MediaStore.Audio.AudioColumns.DATA)
                        val albumId =
                            it.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM_ID)

                        do {
                            val audioArtist = it.getString(artist)
                            val audioYear = it.getInt(year)
                            val audioTrack = it.getInt(track)
                            val audioTitle = it.getString(title)
                            val audioDuration = it.getLong(duration)
                            val audioAlbum = it.getString(album)
                            val audioPath = it.getString(path)
                            val audioAlbumId = it.getString(albumId)

                            songs.add(
                                Music(
                                    audioArtist,
                                    audioYear,
                                    audioTrack,
                                    audioTitle,
                                    audioDuration,
                                    audioAlbum,
                                    audioPath,
                                    audioAlbumId
                                )
                            )

                        } while (it.moveToNext())
                        it.close()
                    }
                }
            }
            catch (exception : Exception){

            }

            songs
        }
}