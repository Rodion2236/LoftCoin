package com.rodion2236.loftcoin.ui.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.ExecutorService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RxSchedulersImpl @Inject constructor(executor: ExecutorService) : RxSchedulers {

    private var ioSchedulers: Scheduler = Schedulers.from(executor)

    override fun io(): Scheduler {
        return ioSchedulers
    }

    override fun cmp(): Scheduler {
        return Schedulers.computation()
    }

    override fun main(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}