package io.kaeawc.app

import android.app.Application
import io.kaeawc.launch.LaunchApp
import io.kaeawc.launch.LaunchComponent
import timber.log.Timber

class App : Application(), LaunchApp {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        component = AppComponent.init(this)
    }

    override fun getLaunchComponent(): LaunchComponent {
        return component.launchBuilder().build()
    }
}
