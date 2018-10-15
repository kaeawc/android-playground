package io.kaeawc.launch

import io.kaeawc.github.GithubModule
import io.kaeawc.storage.StorageModule
import dagger.Subcomponent

@LaunchScope
@Subcomponent(modules = [GithubModule::class, StorageModule::class])
interface LaunchComponent {

    fun inject(launchActivity: LaunchActivity)

    @Subcomponent.Builder
    interface Builder {
        fun storageModule(context: StorageModule): Builder
        fun build(): LaunchComponent
    }
}
