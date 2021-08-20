package com.aseemwangoo.handsonkotlin.compose

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import com.aseemwangoo.handsonkotlin.*
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

        composeTestRule.onNodeWithText(TITLE_MAIN)
            .assertIsDisplayed()
    }

    @Test
    fun testIfTitleClicked() {
        composeTestRule.setContent {
            HomeView(navController)
        }

        composeTestRule.onNodeWithText(TITLE_MAIN)
            .assertHasNoClickAction()

    }

    @Test
    fun testForAddTodoClick() {
        composeTestRule.setContent {
            NavigationComponent()
        }

        composeTestRule.onNodeWithText(ADD_TODO)
            .performClick()

        composeTestRule.onNodeWithText(
            text = SAVE_TODO
        ).assertExists()
    }

    @Test
    fun testForNotSavingTodo() {
        composeTestRule.setContent {
            NavigationComponent()
        }

        composeTestRule.onNodeWithText(ADD_TODO)
            .performClick()

        composeTestRule.onNodeWithText(
            text = SAVE_TODO
        ).assertExists()

        Espresso.pressBack()

        composeTestRule.onNodeWithText(TITLE_MAIN)
            .assertExists()
    }

    @Test
    fun testForTodoInputField() {
        composeTestRule.setContent {
            AddView(navController)
        }
        val dummyText = "Dummy Text"

        composeTestRule.onNodeWithTag(TEST_INPUT_TAG).performTextInput(dummyText)

        composeTestRule.onNodeWithTag(TEST_INPUT_TAG).assertTextEquals(dummyText)
    }
}