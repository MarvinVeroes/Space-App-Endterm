package com.example.marvinveroes_endterm.view.presentation.detail


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.marvinveroes_endterm.data.repository.RocketRepositoryImpl
import com.example.marvinveroes_endterm.domain.repository.RocketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RocketDetailViewModel(
    app: Application,
    private val rocketId: String
) : AndroidViewModel(app) {

    private val repository: RocketRepository = RocketRepositoryImpl(app.applicationContext)

    private val _uiState = MutableStateFlow(RocketDetailUiState())
    val uiState: StateFlow<RocketDetailUiState> = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        observeRocket()
    }


    private fun observeRocket() {
        viewModelScope.launch {
            repository.observeRocketById(rocketId).collectLatest { rocket ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        rocket = rocket,
                        errorMessage = if (rocket == null) "No se encontró el cohete." else null
                    )
                }
            }
        }
    }
}

data class RocketDetailUiState(
    val isLoading: Boolean = true,
    val rocket: com.example.marvinveroes_endterm.domain.model.Rocket? = null,
    val errorMessage: String? = null
)

class RocketDetailViewModelFactory(
    private val app: Application,
    private val rocketId: String
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RocketDetailViewModel(app, rocketId) as T
    }
}
