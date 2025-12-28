package com.example.marvinveroes_endterm.view.auth.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent: SharedFlow<LoginUiEvent> = _uiEvent

    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email, emailError = null) }
        updateLoginEnabled()
    }

    fun onPasswordChanged(password: String) {
        _uiState.update { it.copy(password = password, passwordError = null) }
        updateLoginEnabled()
    }

    fun onLoginClick() {
        val email = _uiState.value.email.trim()
        val password = _uiState.value.password

        val emailError = validateEmail(email)
        val passwordError = validatePassword(password)

        if (emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(
                    emailError = emailError,
                    passwordError = passwordError
                )
            }
            updateLoginEnabled()
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val ok = (email == VALID_EMAIL && password == VALID_PASSWORD)

            _uiState.update { it.copy(isLoading = false) }

            if (ok) {
                _uiEvent.emit(LoginUiEvent.NavigateToHome)
            } else {
                _uiEvent.emit(
                    LoginUiEvent.ShowSnackbar("Credenciales incorrectas. Inténtalo de nuevo.")
                )
            }
        }
    }

    private fun updateLoginEnabled() {
        val emailOk = isEmailValid(_uiState.value.email.trim())
        val passOk = _uiState.value.password.length >= 6
        _uiState.update { it.copy(isLoginEnabled = emailOk && passOk) }
    }

    private fun validateEmail(email: String): String? {
        if (email.isBlank()) return "El email no puede estar vacío"
        if (!isEmailValid(email)) return "Introduce un email válido"
        return null
    }

    private fun validatePassword(password: String): String? {
        if (password.isBlank()) return "La contraseña no puede estar vacía"
        if (password.length < 6) return "La contraseña debe tener al menos 6 caracteres"
        return null
    }

    private fun isEmailValid(email: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(email).matches()

    companion object {
        private const val VALID_EMAIL = "admin@lasalle.es"
        private const val VALID_PASSWORD = "admin1234"
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",

    val emailError: String? = null,
    val passwordError: String? = null,

    val isLoading: Boolean = false,
    val isLoginEnabled: Boolean = false
)

sealed interface LoginUiEvent {
    data class ShowSnackbar(val message: String) : LoginUiEvent
    data object NavigateToHome : LoginUiEvent
}
