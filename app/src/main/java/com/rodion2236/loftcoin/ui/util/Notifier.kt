package com.rodion2236.loftcoin.ui.util

import io.reactivex.Completable

interface Notifier {

    fun sendMessage(title: String, message: String, receiver: Class<*>): Completable

}