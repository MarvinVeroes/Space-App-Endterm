package com.example.marvinveroes_endterm

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
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
        rule.waitUntil(timeoutMillis = 5_000) {
            rule.onAllNodesWithTag("home_title").fetchSemanticsNodes().isNotEmpty()
        }
        rule.onNodeWithTag("home_title").assertIsDisplayed()
    }

    @Test
    fun activeFilter_showsOnlyActiveRockets() {
        // 1) Esperar login
        rule.waitUntil(15_000) {
            rule.onAllNodesWithTag("login_email").fetchSemanticsNodes().isNotEmpty()
        }

        // 2) Login válido
        rule.onNodeWithTag("login_email").performTextClearance()
        rule.onNodeWithTag("login_email").performTextInput("admin@lasalle.es")

        rule.onNodeWithTag("login_password").performTextClearance()
        rule.onNodeWithTag("login_password").performTextInput("admin1234")

        rule.onNodeWithTag("login_button").performClick()

        // 3) Esperar Home (clave)
        rule.waitUntil(15_000) {
            rule.onAllNodesWithTag("home_screen").fetchSemanticsNodes().isNotEmpty()
        }

        // 4) Esperar a que exista el switch (debería estar siempre)
        rule.waitUntil(15_000) {
            rule.onAllNodesWithTag("filter_active_switch").fetchSemanticsNodes().isNotEmpty()
        }

        // 5) Activar filtro
        rule.onNodeWithTag("filter_active_switch").performClick()

        // 6) Esperar a que termine el loading (o haya lista/empty)
        rule.waitUntil(15_000) {
            val hasItems = rule.onAllNodesWithTag("rocket_item").fetchSemanticsNodes().isNotEmpty()
            val hasEmpty =
                rule.onAllNodesWithTag("home_empty_state").fetchSemanticsNodes().isNotEmpty()
            hasItems || hasEmpty
        }

        // 7) Si hay items, todos deben ser active
        val nodes = rule.onAllNodesWithTag("rocket_item").fetchSemanticsNodes()
        if (nodes.isNotEmpty()) {
            nodes.forEach { node ->
                val state = node.config.getOrNull(SemanticsProperties.StateDescription)
                assert(state == "active")
            }
        }
    }
}