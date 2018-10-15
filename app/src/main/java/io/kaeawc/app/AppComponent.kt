package io.kaeawc.app

import io.kaeawc.domain.ApplicationScope
import io.kaeawc.launch.LaunchComponent
import dagger.Component
import io.kaeawc.github.GithubModule
import io.kaeawc.storage.StorageModule

@ApplicationScope
@Component(modules = [StorageModule::class, GithubModule::class])
interface AppComponent {

    fun launchBuilder(): LaunchComponent.Builder

    companion object {
        fun init(app: App): AppComponent =
                DaggerAppComponent.builder()
                        .storageModule(StorageModule(app))
                        .build()
    }
}
