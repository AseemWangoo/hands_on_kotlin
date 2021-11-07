package com.aseemwangoo.handsonkotlin.compose

import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import com.aseemwangoo.handsonkotlin.ADD_TODO
import com.aseemwangoo.handsonkotlin.HomeView
import com.aseemwangoo.handsonkotlin.SAVE_TODO
import com.aseemwangoo.handsonkotlin.TEST_INPUT_TAG
import com.aseemwangoo.handsonkotlin.TITLE_MAIN
import com.aseemwangoo.handsonkotlin.addtodo.view.AddTodoView
import com.aseemwangoo.handsonkotlin.google.GoogleUserModel
import com.aseemwangoo.handsonkotlin.shared.navigation.NavigationComponent
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
            HomeView(
                navController,
                GoogleUserModel(
                    email = "email",
                    name = "name",
                )
            )
        }

        composeTestRule.onNodeWithText(TITLE_MAIN)
            .assertIsDisplayed()
    }

    @Test
    fun testIfTitleClicked() {
        composeTestRule.setContent {
            HomeView(
                navController,
                GoogleUserModel(
                    email = "email",
                    name = "name",
                )
            )
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
            AddTodoView(navController)
        }
        val dummyText = "Dummy Text"

        composeTestRule.onNodeWithTag(TEST_INPUT_TAG).performTextInput(dummyText)

        composeTestRule.onNodeWithTag(TEST_INPUT_TAG).assertTextEquals(dummyText)
    }
}
