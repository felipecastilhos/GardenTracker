package com.felipecastilhos.gardentracker.features.mygarden

import javax.inject.Inject

class MyGardenRepository @Inject constructor(private val myGardenService: MyGardenService) {
    fun listPlants(): Result<List<String>> = myGardenService.listPlants()
    fun addPlant(name: String) = myGardenService.addPlant(name)
}

class MyGardenService {
    private val plants: MutableList<String> = mutableListOf()

    fun listPlants(): Result<List<String>> = Result.success(plants.toList())
    fun addPlant(name: String): Result<List<String>> {
        plants.add(name)
        return Result.success(plants.toList())
    }
}
