package com.rodion2236.loftcoin

import android.app.Application
import android.content.Context
import android.os.StrictMode
import androidx.viewbinding.BuildConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.rodion2236.loftcoin.core.BaseComponent
import com.rodion2236.loftcoin.core.DaggerAppComponent
import com.rodion2236.loftcoin.ui.util.DebugTree
import timber.log.Timber

class LoftApp: Application() {

    lateinit var component: BaseComponent

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
                    .build()
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects()
                    .penaltyLog()
                    .penaltyDeath()
                    .build()
            )
            Timber.plant(DebugTree())
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.e(task.exception, "Fetching FCM registration token failed")
                return@addOnCompleteListener
            }
            Timber.d("fcm: %s", task.result)
        }

        component = DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}

val Context.baseComponent: BaseComponent
    get() = when (this) {
        is LoftApp -> baseComponent
        else -> this.applicationContext.baseComponent
    }