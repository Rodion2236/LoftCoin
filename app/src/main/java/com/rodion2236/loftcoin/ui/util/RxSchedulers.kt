package com.rodion2236.loftcoin.ui.util

import io.reactivex.Scheduler

interface RxSchedulers {

    fun io(): Scheduler
    fun cmp(): Scheduler
    fun main(): Scheduler
}