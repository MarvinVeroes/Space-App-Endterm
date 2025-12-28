package com.example.marvinveroes_endterm.view.presentation.home

sealed interface HomeUiEvent {
    data class ShowSnackbar(val message: String) : HomeUiEvent
}