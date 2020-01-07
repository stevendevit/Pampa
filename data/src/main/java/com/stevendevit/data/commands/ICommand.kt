package com.stevendevit.data.commands

import android.content.Context
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable

/**
 * Created by stevendevit on 01/01/2020.
 * tankadeveloper@gmail.com
 */
interface ICommand<T> {

    fun setup(context: Context)

    fun perform(): @NonNull Observable<T>
}
