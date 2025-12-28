package com.example.marvinveroes_endterm.view.auth.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState

    fun onChangeMode() {
        _uiState.update {
            it.copy(
                isPhoneMode = !it.isPhoneMode,
                value = "",
                errorText = null,
                isRegisterEnabled = false
            )
        }
    }

    fun onRegisterChanged(value: String) {
        _uiState.update { state ->
            val newValue = if (state.isPhoneMode) value else value.trim()

            val error = validate(newValue, state.isPhoneMode)
            val enabled = error == null && newValue.isNotBlank()

            state.copy(
                value = newValue,
                errorText = error,
                isRegisterEnabled = enabled
            )
        }
    }

    /**
     * Validación:
     * - Teléfono: mínimo 9 caracteres (MVP)
     * - Email: patrón de email válido
     */
    private fun validate(value: String, isPhoneMode: Boolean): String? {
        if (value.isBlank()) return "Este campo no puede estar vacío"

        return if (isPhoneMode) {
            if (value.length < 9) "Introduce un teléfono válido (mín. 9 dígitos)" else null
        } else {
            if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) "Introduce un email válido" else null
        }
    }
}

data class RegisterUiState(
    val value: String = "",
    val isPhoneMode: Boolean = true,
    val errorText: String? = null,
    val isRegisterEnabled: Boolean = false
)
