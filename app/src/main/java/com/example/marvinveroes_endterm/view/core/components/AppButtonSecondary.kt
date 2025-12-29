package com.example.marvinveroes_endterm.view.core.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * AppButtonSecondary
 * Componente reutilizable para botones secundarios con borde y texto personalizados.
 */
@Composable
fun AppButtonSecondary(
    modifier: Modifier = Modifier, onClick: () -> Unit, border: BorderStroke = BorderStroke(
        1.dp,
        MaterialTheme.colorScheme.primary
    ),
    title: String,
    tittleColor: Color = MaterialTheme.colorScheme.primary
) {
    OutlinedButton(modifier = modifier, onClick = onClick, border = border
    ) {
        AppText(
            text = title,
            color = tittleColor
        )
    }
}