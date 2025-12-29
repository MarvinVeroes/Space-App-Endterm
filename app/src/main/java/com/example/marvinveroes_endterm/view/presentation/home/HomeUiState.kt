package com.example.marvinveroes_endterm.view.presentation.home

// Estado de la UI para la pantalla principal
data class HomeUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val rockets: List<RocketItemUi> = emptyList(),
    val showOnlyActive: Boolean = false

)