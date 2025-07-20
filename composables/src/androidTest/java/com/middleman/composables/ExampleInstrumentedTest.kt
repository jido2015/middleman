package com.middleman.composables

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.input.TextFieldValue
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.middleman.composables.textfield.BorderlessTextField

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun textField_autoRequestsFocusOnLaunch() {
        composeTestRule.setContent {
            val focusRequester = FocusRequester()
            var text = TextFieldValue("")


        }

        // 🔍 Wait until the field gains focus
        composeTestRule.waitUntil(
            timeoutMillis = 5_000,
            condition = {
                composeTestRule
                    .onNodeWithTag("borderlessTextField")
                    .fetchSemanticsNode()
                    .config
                    .getOrNull(SemanticsProperties.Focused) == true
            }
        )

        // ✅ Then assert it is focused
        composeTestRule
            .onNodeWithTag("borderlessTextField")
            .assertIsFocused()
    }
}