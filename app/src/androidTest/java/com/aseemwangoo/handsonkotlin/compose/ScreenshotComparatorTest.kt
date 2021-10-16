package com.aseemwangoo.handsonkotlin.compose

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.captureToImage
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.aseemwangoo.handsonkotlin.components.addTodo.AddView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.FileOutputStream

class ScreenshotComparatorTest {

    private lateinit var navController: TestNavHostController

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    }

    @Test
    fun testAddTodoScreen() {
        composeTestRule.setContent {
            AddView(navController)
        }

        assertScreenshotMatchesGolden("add_todo", composeTestRule.onRoot())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun assertScreenshotMatchesGolden(
        goldenName: String,
        node: SemanticsNodeInteraction
    ) {
        val bitmap = node.captureToImage().asAndroidBitmap()

        // Save screenshot to file for debugging
        saveScreenshot(goldenName + System.currentTimeMillis().toString(), bitmap)
        val golden = InstrumentationRegistry.getInstrumentation()
            .context.resources.assets.open("$goldenName.png").use { BitmapFactory.decodeStream(it) }

        golden.compare(bitmap)
    }

    private fun saveScreenshot(filename: String, bmp: Bitmap) {
        val path = InstrumentationRegistry.getInstrumentation().targetContext.filesDir.canonicalPath
        FileOutputStream("$path/$filename.png").use { out ->
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
        }
        println("Saved screenshot to $path/$filename.png")
    }

    private fun Bitmap.compare(other: Bitmap) {
        if (this.width != other.width || this.height != other.height) {
            throw AssertionError("Size of screenshot does not match golden file (check device density)")
        }
        // Compare row by row to save memory on device
        val row1 = IntArray(width)
        val row2 = IntArray(width)
        for (column in 0 until height) {
            // Read one row per bitmap and compare
            this.getRow(row1, column)
            other.getRow(row2, column)
            if (!row1.contentEquals(row2)) {
                throw AssertionError("Sizes match but bitmap content has differences")
            }
        }
    }

    private fun Bitmap.getRow(pixels: IntArray, column: Int) {
        this.getPixels(pixels, 0, width, 0, column, width, 1)
    }
}
