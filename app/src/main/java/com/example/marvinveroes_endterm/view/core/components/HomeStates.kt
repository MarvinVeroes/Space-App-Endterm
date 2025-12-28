package com.example.marvinveroes_endterm.view.core.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.material.icons.outlined.WifiOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.example.marvinveroes_endterm.R
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo

@Composable
fun HomeLoadingState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .liveRegionContainer("Estado: cargando cohetes", "\"Cargando\""),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.HourglassEmpty,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(36.dp)
        )
        Spacer(Modifier.height(12.dp))
        CircularProgressIndicator(
            modifier = Modifier.semantics {
                progressBarRangeInfo = ProgressBarRangeInfo.Indeterminate
            }
        )
        Spacer(Modifier.height(12.dp))
        AppText(
            text = stringResource(R.string.home_screen_rocket_loading_state),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun HomeEmptyState() {
    Column(
        modifier = Modifier
            .testTag("home_empty_state")
            .fillMaxSize()
            .liveRegionContainer("Estado: sin resultados", "Sin resultados"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.SearchOff,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(44.dp)
        )
        Spacer(Modifier.height(12.dp))
        AppText(
            text = stringResource(R.string.home_screen_rocket_no_result),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.semantics { heading() }
        )
        Spacer(Modifier.height(6.dp))
        AppText(
            text = stringResource(R.string.home_screen_rocket_try_other_name),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun HomeErrorState(
    message: String?,
    onRetry: () -> Unit
) {
    val text = message ?: stringResource(R.string.home_screen_rocket_error_loading)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .liveRegionContainer("Estado: error de carga $text", "Error"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.WifiOff,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(44.dp)
        )
        Spacer(Modifier.height(12.dp))
        AppText(
            text = text,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(Modifier.height(14.dp))
        Button(onClick = onRetry) {
            AppText(text = stringResource(R.string.home_screen_rocket_error_retry))
        }
    }
}

// --- helper para no repetir semantics ---
private fun Modifier.liveRegionContainer(
    contentDesc: String,
    stateDesc: String
): Modifier = semantics {
    liveRegion = LiveRegionMode.Polite
    contentDescription = contentDesc
    stateDescription = stateDesc
}