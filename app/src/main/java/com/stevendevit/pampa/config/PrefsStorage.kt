package com.stevendevit.pampa.config

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.stevendevit.shared.ext.IntPreference

/**
 * Created by stevendevit on 19/01/2020.
 * tankadeveloper@gmail.com
 */
class PreferenceStorage(context: Context) : IPreferenceStorage {

    private val prefs: Lazy<SharedPreferences> = lazy {
        context.applicationContext.getSharedPreferences(
            PREFS_NAME, MODE_PRIVATE
        ).apply {
            registerOnSharedPreferenceChangeListener(changeListener)
        }
    }

    override var titleMaxWords by IntPreference(prefs, PREFS_TITLE_MAX_WORDS, 20)

    private val changeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->

    }

    companion object {
        const val PREFS_NAME = "Pampa"
        const val PREFS_TITLE_MAX_WORDS = "PREFS_TITLE_MAX_WORDS"
    }
}

interface IPreferenceStorage {
    var titleMaxWords: Int
}



