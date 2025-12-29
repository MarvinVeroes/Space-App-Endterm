package com.example.marvinveroes_endterm.view.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ExitToApp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.marvinveroes_endterm.R
import com.example.marvinveroes_endterm.view.core.components.AppText
import com.example.marvinveroes_endterm.view.core.components.AppTextField
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.request.crossfade
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.stateDescription
import com.example.marvinveroes_endterm.view.core.components.HomeEmptyState
import com.example.marvinveroes_endterm.view.core.components.HomeErrorState
import com.example.marvinveroes_endterm.view.core.components.HomeLoadingState
import kotlinx.coroutines.flow.collectLatest

/**
 * Pantalla principal que muestra la lista de cohetes
 * Permite buscar y filtrar cohetes activos
 * Maneja estados de carga, error y lista vacía
 * Soporta navegación al detalle del cohete y cierre de sesión
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onRocketClick: (String) -> Unit = {},
    homeViewModel: HomeViewModel = viewModel()
) {
    val uiState = homeViewModel.uiState.collectAsStateWithLifecycle().value

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        homeViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is HomeUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.testTag("home_screen"),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    AppText(
                        modifier = Modifier.testTag("home_title"),
                        text = stringResource(R.string.home_screen_title),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                actions = {
                    IconButton(
                        onClick = onLogout,
                        modifier = Modifier.semantics {
                            contentDescription = "Cerrar sesion"
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ExitToApp,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 24.dp)
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(8.dp))

            AppTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.query,
                onValueChange = homeViewModel::onQueryChanged,
                label = stringResource(R.string.home_screen_rocket_finder),
                placeholder = stringResource(R.string.home_screen_rocket_finder_placeholder),
                singleLine = true
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppText(text = stringResource(R.string.home_screen_view_only_active))

                Switch(
                    modifier = Modifier.testTag("filter_active_switch"),
                    checked = uiState.showOnlyActive,
                    onCheckedChange = homeViewModel::onShowOnlyActiveChanged
                )
            }

            Spacer(Modifier.height(16.dp))

            when {
                uiState.isLoading && uiState.rockets.isEmpty() -> {
                    HomeLoadingState()
                }

                uiState.errorMessage != null && uiState.rockets.isEmpty() -> {
                    HomeErrorState(message = uiState.errorMessage, onRetry = homeViewModel::retry)
                }

                uiState.rockets.isEmpty() -> {
                    HomeEmptyState()
                }
                else -> {
                    RocketsList(
                        rockets = uiState.rockets,
                        onRocketClick = onRocketClick

                    )
                }
            }
        }
    }
}

@Composable
private fun RocketsList(
    rockets: List<RocketItemUi>,
    onRocketClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(rockets, key = { it.id }) { rocket ->
            RocketRow(
                rocket = rocket,
                onClick = { onRocketClick(rocket.id) }
            )
        }
    }
}

@Composable
private fun RocketRow(
    rocket: RocketItemUi,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("rocket_item_${rocket.id}")
            .semantics {
                stateDescription = if (rocket.isActive) "active" else "inactive"
                role = Role.Button
                contentDescription = "Abrir detalle del cohete ${rocket.name}"
            },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val context = LocalContext.current

            Surface(
                modifier = Modifier.size(44.dp),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(rocket.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Imagen de ${rocket.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium),
                    error = painterResource(R.drawable.ic_rocket_placeholder),
                    placeholder = painterResource(R.drawable.ic_rocket_placeholder),
                    fallback = painterResource(R.drawable.ic_rocket_placeholder)
                )
            }

            Spacer(Modifier.width(12.dp))

            Column(Modifier.weight(1f)) {
                AppText(
                    text = rocket.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(2.dp))
                AppText(
                    text = stringResource(R.string.home_screen_details),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
