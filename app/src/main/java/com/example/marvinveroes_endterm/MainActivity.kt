package com.example.marvinveroes_endterm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.marvinveroes_endterm.ui.theme.MarvinVeroesEndtermTheme
import com.example.marvinveroes_endterm.view.core.navigation.NavigationWrapper

/**
 * MainActivity que sirve como punto de entrada de la aplicación
 * Configura el tema y el contenedor de navegación
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MarvinVeroesEndtermTheme {
                NavigationWrapper()
            }
        }
    }
}