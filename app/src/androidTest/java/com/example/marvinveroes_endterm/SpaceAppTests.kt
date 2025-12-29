package com.example.marvinveroes_endterm

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.getOrNull
import androidx.compose.ui.test.SemanticsMatcher
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

/**
 * Tests de la aplicación SpaceApp.
 * Incluye pruebas de login y filtrado de cohetes activos.
 * Las credenciales de login validas son:
 * Email: admin@lasalle.es
 * Password: admin1234
 */

// Matcher personalizado para buscar nodos con un prefijo específico en el testTag
private fun hasTestTagPrefix(prefix: String) =
    SemanticsMatcher("Has testTag prefix: $prefix") { node ->
        val tag = node.config.getOrNull(SemanticsProperties.TestTag)
        tag?.startsWith(prefix) == true
    }

@RunWith(AndroidJUnit4::class)
class SpaceAppTests {

    @get:Rule
    val rule = createAndroidComposeRule<MainActivity>()

    // Test de login válido
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

    // Test de filtro de cohetes activos
    @Test
    fun activeFilter_showsOnlyActiveRockets() {
        //  Esperar login
        rule.waitUntil(15_000) {
            rule.onAllNodesWithTag("login_email").fetchSemanticsNodes().isNotEmpty()
        }

        //  Login válido
        rule.onNodeWithTag("login_email").performTextClearance()
        rule.onNodeWithTag("login_email").performTextInput("admin@lasalle.es")

        rule.onNodeWithTag("login_password").performTextClearance()
        rule.onNodeWithTag("login_password").performTextInput("admin1234")

        rule.onNodeWithTag("login_button").performClick()

        //  Esperar Home
        rule.waitUntil(15_000) {
            rule.onAllNodesWithTag("home_screen").fetchSemanticsNodes().isNotEmpty()
        }

        //  Esperar switch
        rule.waitUntil(15_000) {
            rule.onAllNodesWithTag("filter_active_switch").fetchSemanticsNodes().isNotEmpty()
        }

        //  Activar filtro
        rule.onNodeWithTag("filter_active_switch").performClick()

        //  Esperar resultado: lista (items) o empty o error
        rule.waitUntil(15_000) {
            val hasItems = rule.onAllNodes(hasTestTagPrefix("rocket_item_")).fetchSemanticsNodes().isNotEmpty()
            val hasEmpty = rule.onAllNodesWithTag("home_empty_state").fetchSemanticsNodes().isNotEmpty()
            val hasError = rule.onAllNodesWithTag("home_error_state").fetchSemanticsNodes().isNotEmpty()
            hasItems || hasEmpty || hasError
        }

        // Si entro en error, el test debe fallar por no poder verificar el filtro
        val errorNodes = rule.onAllNodesWithTag("home_error_state").fetchSemanticsNodes()
        if (errorNodes.isNotEmpty()) {
            throw AssertionError("Home está en estado de error, no se pudo verificar el filtro de activos.")
        }

        //  Si hay items, todos deben ser active
        val nodes = rule.onAllNodes(hasTestTagPrefix("rocket_item_")).fetchSemanticsNodes()
        if (nodes.isNotEmpty()) {
            nodes.forEach { node ->
                val state = node.config.getOrNull(SemanticsProperties.StateDescription)
                assert(state == "active")
            }
        }
    }

}