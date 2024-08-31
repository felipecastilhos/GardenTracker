package com.felipecastilhos.gardentracker

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.felipecastilhos.gardentracker.features.home.HomeScreen
import com.felipecastilhos.gardentracker.features.home.HomeViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

internal class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private class TestSubject(val homeViewModel: HomeViewModel) {
        companion object {
            fun createWith( homeViewModel: HomeViewModel = mockk()) = TestSubject(homeViewModel)
        }
    }

    @Test
    fun homeScreen_DisplaysHomeTab() {
        composeTestRule.setContent {
            with(TestSubject.createWith()) {
                HomeScreen(onNavigate = {}, homeViewModel = homeViewModel)
            }
        }

        composeTestRule.onNodeWithText("Continue").assertIsDisplayed()
    }

}