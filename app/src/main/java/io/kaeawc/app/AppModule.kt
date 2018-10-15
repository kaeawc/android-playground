package io.kaeawc.app

import io.kaeawc.domain.ApplicationScope
import dagger.Module
import dagger.Provides
import io.kaeawc.domain.Router

@Module
class AppModule {

    @Provides
    @ApplicationScope
    fun providesRouter(): Router = RouterImpl()

}
