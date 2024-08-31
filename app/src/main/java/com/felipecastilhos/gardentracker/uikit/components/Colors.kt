package com.felipecastilhos.gardentracker.uikit.components

import androidx.compose.ui.graphics.Color

sealed class ColorTokens(val color: Color) {
    sealed class PrimaryColors {
        data object LeafGreen : ColorTokens(Color(0xFF4caf50))
        data object SunflowerYellow : ColorTokens(Color(0xFFFFEB3B))
        data object EarthBrown : ColorTokens(Color(0xFF795548))
    }

    sealed class SecondaryColors {
        data object SkyBlue : ColorTokens(Color(0xFF03A9F4))
        data object FlowerPink : ColorTokens(Color(0xFFE91E63))
        data object HoneycombGold : ColorTokens(Color(0xFFFFC107))
    }

    sealed class AccentColors {
        data object BeeBlack : ColorTokens(Color(0xFF212121))
        data object PetalPurple : ColorTokens(Color(0xFF9C27B0))
        data object HoneycombGold : ColorTokens(Color(0xFFD7CCC8))
    }

    sealed class BackgroundColors {
        data object CloudWhite : ColorTokens(Color(0xFFFFFFFF))
        data object SoftGreen : ColorTokens(Color(0xFFE8F5E9))
        data object LightYellow : ColorTokens(Color(0xFFFFFDE7))
    }

    sealed class Disabled {
        data object DisabledCharcoalGray : ColorTokens(Color(0xFFBDBDBD))
        data object DisabledDarkGreen : ColorTokens(Color(0xFF00BBD0))
    }
}