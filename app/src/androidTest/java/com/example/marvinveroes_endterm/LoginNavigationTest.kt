package com.example.marvinveroes_endterm

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginNavigationTest {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    @Test
    fun validLogin_navigatesToHome() {
        rule.waitUntil(timeoutMillis = 5_000) {
            rule.onAllNodesWithTag("login_email").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("login_email").performTextInput("admin@lasalle.es")
        rule.onNodeWithTag("login_password").performTextInput("admin1234")
        rule.onNodeWithTag("login_button").performClick()

        // Verifica que estamos en Home
        rule.waitUntil(timeoutMillis = 5_000){
            rule.onAllNodesWithTag("home_title").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("home_title").assertIsDisplayed()
    }
}