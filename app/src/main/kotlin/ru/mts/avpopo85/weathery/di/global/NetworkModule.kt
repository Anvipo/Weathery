package ru.mts.avpopo85.weathery.di.global

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.mts.avpopo85.weathery.data.network.NetManager
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideNetManager(context: Context): NetManager = NetManager(context)
}