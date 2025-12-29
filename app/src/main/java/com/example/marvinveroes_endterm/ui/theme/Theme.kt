package com.example.marvinveroes_endterm.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// COLORES PARA MODO OSCURO
private val DarkColorScheme = darkColorScheme(
    // Colores principales
    primary = SpacePrimary,
    onPrimary = Color.White,

    secondary = SpaceSecondary,
    onSecondary = Color.White,

    // Fondo general de la app
    background = SpaceBg,
    onBackground = SpaceText,

    // Superficies
    surface = SpaceSurface,
    onSurface = SpaceText,

    // Variante de sperficie
    surfaceVariant = SpaceSurface,
    onSurfaceVariant = SpaceTextMuted,

    // Bordes
    outline = SpaceOutline,

    // Errores
    error = Color(0xFFFF6B6B),
    onError = Color.White
)

// COLORES PARA MODO CLARO
private val LightColorScheme = lightColorScheme(
    // Colores principales
    primary = SpacePrimary,
    onPrimary = Color.White,

    secondary = SpaceSecondary,
    onSecondary = Color.White,

    // Fondo general de la app
    background = Color(0xFFF7F4FF),
    onBackground = Color(0xFF1D1A2B),

    // Superficies
    surface = Color.White,
    onSurface = Color(0xFF1D1A2B),

    // Variante de sperficie
    surfaceVariant = Color(0xFFEDE8FF),
    onSurfaceVariant = Color(0xFF4B4362),

    // Bordes
    outline = Color(0xFFCFC7E6),

    // Errores
    error = Color(0xFFB00020),
    onError = Color.White
)


@Composable
fun MarvinVeroesEndtermTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,      // Desactivado por defecto
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = shapes,
        content = content
    )
}