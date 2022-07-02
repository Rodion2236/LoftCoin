package com.rodion2236.loftcoin.data.fcm

import com.rodion2236.loftcoin.core.BaseComponent
import dagger.Component

@Component(
    modules = [
        FcmModule::class
    ],
    dependencies = [
        BaseComponent::class
    ]
)
abstract class FcmComponent {

    abstract fun inject(fcmService: FcmService)

}