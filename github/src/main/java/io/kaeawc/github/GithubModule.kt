package io.kaeawc.github

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import io.kaeawc.domain.ApplicationScope
import io.kaeawc.domain.LoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
class GithubModule {

    @Provides
    @ApplicationScope
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @ApplicationScope
    @Named("GithubOkhttp")
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(LoggingInterceptor())
                .build()
    }

    @Provides
    @ApplicationScope
    @Named("GithubRetrofit")
    fun provideRetrofit(
            @Named("GithubOkhttp") client: OkHttpClient,
            moshi: Moshi): Retrofit {

        return Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
                .addConverterFactory(EmptyResponseFactory())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
    }

    @Provides
    @ApplicationScope
    fun provideGithubApi(@Named("GithubRetrofit") retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    @Provides
    @ApplicationScope
    fun provideGithubGateway(githubApi: GithubApi): GithubGateway {
        return GithubGateway(githubApi)
    }
}
