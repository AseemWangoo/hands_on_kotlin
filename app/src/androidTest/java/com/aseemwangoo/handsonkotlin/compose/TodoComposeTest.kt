package com.aseemwangoo.handsonkotlin.compose

import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import com.aseemwangoo.handsonkotlin.HomeView
import com.aseemwangoo.handsonkotlin.components.addTodo.AddView
import com.aseemwangoo.handsonkotlin.components.navigation.NavigationComponent
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TodoComposeTest {

    private lateinit var navController: TestNavHostController

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testIfTitleExists() {
        composeTestRule.setContent {
            HomeView(navController)
        }

        composeTestRule.onNodeWithText("My ToDo List")
            .assertIsDisplayed()

    }

    @Test
    fun testIfTitleClicked() {
        composeTestRule.setContent {
            HomeView(navController)
        }

        composeTestRule.onNodeWithText("My ToDo List")
            .assertHasNoClickAction()

    }

    @Test
    fun testForAddTodoClick() {
        composeTestRule.setContent {
            NavigationComponent()
        }

        composeTestRule.onNodeWithText("Add Todo")
            .performClick()

        composeTestRule.onNodeWithText(
            text = "Save Todo"
        ).assertExists()
    }

    @Test
    fun testForTodoInputField() {
        composeTestRule.setContent {
            AddView(navController)
        }

        composeTestRule.onNodeWithText(
            text = "Save Todo"
        ).assertExists()
    }
}