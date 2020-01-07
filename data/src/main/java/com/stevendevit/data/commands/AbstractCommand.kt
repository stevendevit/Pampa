package com.stevendevit.data.commands

import android.content.Context
import com.stevendevit.domain.model.CommandMetaData
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
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

    override fun perform(): @NonNull Observable<T> {

        return Observable.empty()
    }

    open class AbstractCommandResult<T> {

        open fun type(): Class<T>? {

            return null
        }
    }

    data class CommandErrorData(val klazzString: String, val cause: String)
}
