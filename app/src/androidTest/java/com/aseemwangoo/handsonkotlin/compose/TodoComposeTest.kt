package com.aseemwangoo.handsonkotlin.compose

import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.aseemwangoo.handsonkotlin.HomeView
import org.junit.Rule
import org.junit.Test

class TodoComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testIfTitleExists() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            HomeView(navController)
        }

        composeTestRule.onNodeWithText("My ToDo List")
            .assertIsDisplayed()

    }

    @Test
    fun testIfTitleClicked() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())

        composeTestRule.setContent {
            HomeView(navController)
        }

        composeTestRule.onNodeWithText("My ToDo List")
            .assertHasNoClickAction()

    }

}