package com.stevendevit.domain.commands

import android.content.Context
import com.stevendevit.domain.model.CommandMetaData
import com.stevendevit.shared.data.Either
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import java.lang.Exception
import java.lang.ref.WeakReference

/**
 * Created by stevendevit on 02/01/2020.
 * tankadeveloper@gmail.com
 */
abstract class AbstractCommand<T>(val commandMetaData: CommandMetaData) :
    ICommand<T> {

    lateinit var context: WeakReference<Context>

    override fun setup(context: Context) {
        this.context = WeakReference(context)
    }

    abstract override suspend fun perform(): T

    interface Result<T>

    abstract class Failure(val throwable: Throwable)
}
