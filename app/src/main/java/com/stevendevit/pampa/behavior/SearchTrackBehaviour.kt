package com.stevendevit.pampa.behavior

import com.stevendevit.data.constants.QuestionsConstants
import com.stevendevit.data.constants.SentencesConstants
import com.stevendevit.data.datasource.ext.getSingularOrPlural
import com.stevendevit.domain.interfaces.ISpeech
import com.stevendevit.domain.interfaces.ISpeechListener
import com.stevendevit.domain.interfaces.ITextToSpeechCallback
import com.stevendevit.domain.model.Music
import com.stevendevit.domain.model.SpeechResult
import com.stevendevit.domain.table.INumberTable
import com.stevendevit.domain.table.IQuestionTable
import com.stevendevit.domain.table.ISentenceTable
import com.stevendevit.pampa.command.commands.SearchTrackCommand
import com.stevendevit.pampa.music.MusicPlayerInteractor
import com.stevendevit.shared.ext.whenNullOrEmpty
import com.stevendevit.shared.scheduler.DefaultSchedulerProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * Created by stevendevit on 02/01/2020.
 * tankadeveloper@gmail.com
 */
class SearchTrackBehaviour(
    private val commandResult: SearchTrackCommand.SearchTrackCommandResult.Success,
    private val musicPlayerInteractor: MusicPlayerInteractor,
    private val speech: ISpeech,
    private val sentenceTable: ISentenceTable,
    private val questionTable: IQuestionTable,
    private val numberTable: INumberTable
) :
    AbstractBehavior() {
    override fun perform() {

        val list = commandResult.data.list

        list.whenNullOrEmpty {

            val localisedNoSongFound = sentenceTable.getSentence(
                SentencesConstants.SentenceGroup.SENTENCE_TRACK,
                SentencesConstants.SentenceGroupValue.S_TRACK_NOT_FOUND
            )

            speech.say(localisedNoSongFound, object : ITextToSpeechCallback {
            })

            return
        }

        val foundListSentence = sentenceTable.getSentence(
            SentencesConstants.SentenceGroup.SENTENCE_TRACK,
            SentencesConstants.SentenceGroupValue.S_TRACK_FOUND_LIST
        )

        speech.say(
            foundListSentence.getSingularOrPlural(list.size),
            object : ITextToSpeechCallback {

                override fun onCompleted() {
                    super.onCompleted()

                    if (list.size <= 1) {

                        val first = list.first()
                        musicPlayerInteractor.playNow(first, list, false)
                        return
                    }

                    val observer = Observable.just(list).doOnNext {

                        listQuerySongs(it)
                    }.subscribeOn(DefaultSchedulerProvider.io()).subscribe()

                    compositeDisposable.add(observer)
                }
            })
    }

    fun listQuerySongs(songs: List<Music>) {

        var speaking: Boolean
        songs.forEachIndexed { index, music ->

            speaking = true

            val enumerateSongsSentence = sentenceTable.getSentence(
                SentencesConstants.SentenceGroup.SENTENCE_TRACK,
                SentencesConstants.SentenceGroupValue.S_TRACK_LIST_ENUMERATE
            )

            val numberSentence = String.format(enumerateSongsSentence, index + 1)
            val titleSentence = music.title?.take(20) ?: ""

            val fullSentence = numberSentence.plus(titleSentence)

            speech.say(fullSentence,
                object : ITextToSpeechCallback {

                    override fun onError() {
                        speaking = false
                    }

                    override fun onStart() {
                        speaking = true
                    }

                    override fun onCompleted() {
                        speaking = false
                    }

                })

            if (index != songs.size) {

                while (speaking) {

                    // waiting
                    //println("Waiting...")
                }
            }
        }

        val whichSongToListenQuestion = questionTable.getQuestion(
            QuestionsConstants.QuestionGroup.Q_TRACK,
            QuestionsConstants.QuestionGroupValue.Q_TRACK_WHICH_TRACK_TO_LISTEN
        )

        speech.say(whichSongToListenQuestion, object : ITextToSpeechCallback {

            override fun onError() {
                clear()
            }

            override fun onStart() {
            }

            override fun onCompleted() {

                speech.startListening(object : ISpeechListener {
                    override fun onSpeechResult(result: SpeechResult) {

                        val data = result as SpeechResult.Data
                        val localisedText = data.text
                        val number = localisedText.toIntOrNull()

                        if (number != null) {

                            if (number >= 1 && number <= songs.size) {

                                musicPlayerInteractor.playNow(songs[number - 1], songs)
                            } else {

                            }
                        } else {

                            var mappedNumber = -1

                            if (numberTable.isValidNumber(data.text)) {

                                mappedNumber = numberTable.getNumber(data.text)
                            }

                            if (mappedNumber >= 1){

                                musicPlayerInteractor.playNow(songs[mappedNumber - 1], songs)
                            }
                        }
                    }
                })
            }
        })
    }
}
