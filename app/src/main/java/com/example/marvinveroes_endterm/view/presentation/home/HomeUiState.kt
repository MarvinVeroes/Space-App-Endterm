package com.example.marvinveroes_endterm.view.presentation.home


data class HomeUiState(
    val query: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val rockets: List<RocketItemUi> = emptyList()
)