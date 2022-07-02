package com.rodion2236.loftcoin.ui.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.rodion2236.loftcoin.R
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotifierImpl @Inject constructor(
    private val context: Context,
    private val schedulers: RxSchedulers
) : Notifier {

    private val ntfManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


    override fun sendMessage(title: String, message: String, receiver: Class<*>): Completable {
        return Completable.fromAction {
            val channelId = context.getString(R.string.default_channel_id)
            val notification = NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        PendingIntent.getActivity(
                            context,
                            0,
                            Intent(context, receiver),
                            PendingIntent.FLAG_IMMUTABLE,
                            Bundle.EMPTY
                        )
                    } else {
                        PendingIntent.getActivity(
                            context,
                            0,
                            Intent(context, receiver),
                            PendingIntent.FLAG_ONE_SHOT,
                            Bundle.EMPTY
                        )
                    }
                ).build()
            ntfManager.notify(1, notification)
        }
            .startWith(createDefaultChanel())
            .subscribeOn(schedulers.main())
    }

    private fun createDefaultChanel(): Completable = Completable.fromAction {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ntfManager.createNotificationChannel(
                NotificationChannel(
                    context.getString(R.string.default_channel_id),
                    context.getString(R.string.default_channel_name),
                    NotificationManager.IMPORTANCE_LOW
                )
            )
        }
    }
}