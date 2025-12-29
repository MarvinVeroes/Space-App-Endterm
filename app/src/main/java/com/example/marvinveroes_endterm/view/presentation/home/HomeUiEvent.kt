package com.example.marvinveroes_endterm.view.presentation.home

// Eventos de UI para la pantalla principal
sealed interface HomeUiEvent {
    data class ShowSnackbar(val message: String) : HomeUiEvent
}