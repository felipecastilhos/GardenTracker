package com.felipecastilhos.gardentracker.core.dependencyinjection

import com.felipecastilhos.gardentracker.core.coroutines.CoroutineContextProvider
import com.felipecastilhos.gardentracker.core.coroutines.DefaultCoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDispatcherProvider(): CoroutineContextProvider = DefaultCoroutineContextProvider()
}