package com.example.marvinveroes_endterm.view.presentation.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.marvinveroes_endterm.R
import com.example.marvinveroes_endterm.view.core.components.AppText
import androidx.compose.ui.platform.LocalUriHandler
import java.text.NumberFormat
import java.util.Locale

/**
 * Pantalla de detalle de un cohete.
 *
 * Muestra la información detallada de un cohete específico, incluyendo su imagen,
 * nombre, país de origen, primer vuelo, tasa de éxito, etapas, costo por lanzamiento
 * y una descripción. También proporciona un botón para abrir la página de Wikipedia
 * del cohete si está disponible.
 *
 * @param rocketId ID del cohete a mostrar.
 * @param navigateBack Función para navegar hacia atrás en la pila de navegación.
 */


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RocketDetailScreen(
    rocketId: String,
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val vm: RocketDetailViewModel = viewModel(
        factory = RocketDetailViewModelFactory(
            app = context.applicationContext as android.app.Application,
            rocketId = rocketId
        )
    )

    val uiState by vm.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    AppText(
                        text = stringResource(R.string.detail_screen_title),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Back to home"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator()

                uiState.errorMessage != null -> {
                    AppText(
                        text = uiState.errorMessage!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                uiState.rocket != null -> {
                    RocketDetailContent(rocket = uiState.rocket!!)
                }
            }
        }
    }
}

@Composable
private fun RocketDetailContent(rocket: com.example.marvinveroes_endterm.domain.model.Rocket) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .verticalScroll(scrollState)
            .padding(bottom = 24.dp)
    ) {
        Spacer(Modifier.height(12.dp))

        // Imagen
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
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
                    .clip(MaterialTheme.shapes.large),
                placeholder = painterResource(R.drawable.ic_rocket_placeholder),
                error = painterResource(R.drawable.ic_rocket_placeholder),
                fallback = painterResource(R.drawable.ic_rocket_placeholder)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Nombre completo
        AppText(
            text = rocket.name,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(14.dp))

        // Datos relevantes
        DetailFactsCard(
            country = rocket.country,
            firstFlight = rocket.firstFlight,
            successRate = rocket.successRatePct,
            stages = rocket.stages,
            costPerLaunch = rocket.costPerLaunch
        )

        Spacer(Modifier.height(14.dp))

        // Descripción con scroll (ya lo permite el Column scrollable)
        AppText(
            text = stringResource(R.string.detail_screen_description_title),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(6.dp))
        AppText(
            text = rocket.description.ifBlank { stringResource(R.string.detail_screen_no_description) },
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Modifier.height(18.dp))

        val uriHandler = LocalUriHandler.current

        val wikiUrl = rocket.wikipedia?.trim().orEmpty()
        if (wikiUrl.isNotBlank()) {
            Spacer(Modifier.height(18.dp))

            Button(
                onClick = { uriHandler.openUri(wikiUrl) },
                modifier = Modifier.fillMaxWidth()
            ) {
                AppText(
                    text = stringResource(R.string.detail_screen_open_wikipedia),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

    }
}

@Composable
private fun DetailFactsCard(
    country: String,
    firstFlight: String,
    successRate: Int,
    stages: Int,
    costPerLaunch: Long
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            FactRow(label = stringResource(R.string.detail_screen_country), value = country)
            FactRow(
                label = stringResource(R.string.detail_screen_first_flight),
                value = firstFlight
            )
            FactRow(
                label = stringResource(R.string.detail_screen_success_rate),
                value = "$successRate%"
            )
            FactRow(
                label = stringResource(R.string.detail_screen_stages),
                value = stages.toString()
            )
            val formattedCost = remember(costPerLaunch) {
                NumberFormat.getCurrencyInstance(Locale.US).format(costPerLaunch)
            }
            FactRow(
                label = stringResource(R.string.detail_screen_cost),
                value = formattedCost
            )
        }
    }
}

@Composable
private fun FactRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AppText(
            text = label,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(Modifier.width(12.dp))
        AppText(
            text = value,
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
