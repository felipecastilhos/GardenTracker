package com.felipecastilhos.gardentracker.features

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.felipecastilhos.gardentracker.R

enum class HomeDestinations(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    @StringRes val contentDescription: Int,
) {
    HOME(
        label = R.string.home,
        icon = R.drawable.ic_home,
        contentDescription = R.string.go_to_home
    ),
    PLANTS(
        label = R.string.plants,
        icon = R.drawable.ic_my_plants,
        contentDescription = R.string.go_to_tools
    ),
    TOOLS(
        label = R.string.tools,
        icon = R.drawable.ic_box,
        contentDescription = R.string.go_to_tools
    ),
}