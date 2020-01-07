package com.stevendevit.pampa.voice.speech

import android.content.Context
import com.stevendevit.pampa.model.SpeechResult
import net.gotev.speech.Speech
import net.gotev.speech.SpeechDelegate
import net.gotev.speech.TextToSpeechCallback
import java.lang.ref.WeakReference

/**
 * Created by stevendevit on 06/01/2020.
 * tankadeveloper@gmail.com
 */
class SpeechImpl : ISpeech {

    private var weakReference: WeakReference<Context>? = null

    override fun init(context: Context) {

        weakReference = WeakReference(context)
        Speech.init(weakReference!!.get(), context.packageName)
    }

    override fun say(string: String, listener: ITextToSpeechCallback) {

        stopSpeech()
        Speech.getInstance().say(
            string, object : TextToSpeechCallback {
                override fun onError() {

                    listener.onError()
                }

                override fun onStart() {
                    listener.onStart()
                }

                override fun onCompleted() {
                    listener.onCompleted()
                }
            })
    }

    override fun startListening(listener: ISpeechListener) {
        stopListening()
        Speech.getInstance().startListening(null, object : SpeechDelegate {
            override fun onStartOfSpeech() {
                listener.onStartOfSpeech()
            }

            override fun onSpeechPartialResults(results: MutableList<String>?) {
                listener.onSpeechPartialResults(results)
            }

            override fun onSpeechRmsChanged(value: Float) {
                listener.onSpeechRmsChanged(value)
            }

            override fun onSpeechResult(result: String?) {
                listener.onSpeechResult(SpeechResult.Data(result ?: ""))
            }
        })
    }

    override fun stopListening() {

        Speech.getInstance().stopListening()
    }

    override fun stopSpeech() {
        Speech.getInstance().stopTextToSpeech()
    }
}