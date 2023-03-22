package com.yoni.chat.app.features.chatscreen.di

import com.squareup.moshi.Moshi
import com.yoni.chat.app.features.chatscreen.data.DefaultChatRepository
import com.yoni.chat.app.features.chatscreen.data.IChatDataSource
import com.yoni.chat.app.features.chatscreen.data.IChatRepository
import com.yoni.chat.app.features.chatscreen.data.remote.ChatBotRemoteDataSource
import com.yoni.chat.app.features.chatscreen.data.remote.ChatBotService
import com.yoni.chat.server.MockServer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
internal object ChatScreenModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class RemoteDataSource

    @Provides fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .callTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:${MockServer.PORT}/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideChatBotService(retrofit: Retrofit): ChatBotService {
        return retrofit.create(ChatBotService::class.java)
    }

    @RemoteDataSource
    @Provides
    fun provideRemoteChatBotDataSource(chatBotService: ChatBotService): IChatDataSource {
        return ChatBotRemoteDataSource(chatBotService)
    }

    @Provides
    fun provideRepository(
        @RemoteDataSource remote: IChatDataSource
    ): IChatRepository {
        return DefaultChatRepository(remote)
    }

}