package com.rodion2236.loftcoin.data.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rodion2236.loftcoin.R
import com.rodion2236.loftcoin.baseComponent
import com.rodion2236.loftcoin.ui.activity.main.MainActivity
import com.rodion2236.loftcoin.ui.util.Notifier
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Inject

class FcmService : FirebaseMessagingService() {

    private val disposable = CompositeDisposable()

    @Inject
    lateinit var notifier: Notifier

    override fun onCreate() {
        super.onCreate()
        val baseComponent = this.baseComponent
        DaggerFcmComponent
            .builder()
            .baseComponent(baseComponent)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val notification = message.notification
        if (notification != null) {
            disposable.add(
                notifier.sendMessage(
                    Objects.toString(notification.title, getString(R.string.app_name)),
                    Objects.toString(notification.body, "Something wrong"),
                    MainActivity::class.java
                ).subscribe()
            )
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }


    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

}