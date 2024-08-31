package com.felipecastilhos.gardentracker.features.mygarden

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MyGardenModule {
    @Provides
    fun providesMyGardenRepository(service: MyGardenService): MyGardenRepository =
        MyGardenRepository(myGardenService = service)

    @Provides
    fun providesMyGardenService(): MyGardenService = MyGardenService()
}
