package com.stevendevit.shared.scheduler

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers

interface SchedulerProvider {

    fun main() : Scheduler = AndroidSchedulers.mainThread()
    fun io(): Scheduler = Schedulers.io()
    fun single(): Scheduler = Schedulers.single()
    fun newThread(): Scheduler = Schedulers.newThread()
}
