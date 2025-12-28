package com.example.marvinveroes_endterm.view.presentation.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.marvinveroes_endterm.data.repository.RocketRepositoryImpl
import com.example.marvinveroes_endterm.domain.model.Rocket
import com.example.marvinveroes_endterm.domain.repository.RocketRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow


class HomeViewModel(app: Application) : AndroidViewModel(app) {

    private val _uiEvent = MutableSharedFlow<HomeUiEvent>(
        replay = 0,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val uiEvent: SharedFlow<HomeUiEvent> = _uiEvent.asSharedFlow()
    private val repository: RocketRepository = RocketRepositoryImpl(app.applicationContext)

    private val queryFlow = MutableStateFlow("")

    private val isLoadingFlow = MutableStateFlow(false)
    private val errorMessageFlow = MutableStateFlow<String?>(null)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    private val rocketsFlow = queryFlow
        .debounce(350)
        .map { it.trim() }
        .distinctUntilChanged()
        .flatMapLatest { q -> repository.observeRockets(q) }
        .map { list -> list.map { it.toUi() } }
        .onStart { emit(emptyList()) }

    val uiState: StateFlow<HomeUiState> = combine(
        queryFlow,
        isLoadingFlow,
        errorMessageFlow,
        rocketsFlow
    ) { query, isLoading, errorMessage, rockets ->
        HomeUiState(
            query = query,
            isLoading = isLoading,
            errorMessage = errorMessage,
            rockets = rockets
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState()
    )

    init {
        refresh()
    }

    fun onQueryChanged(newQuery: String) {
        queryFlow.value = newQuery
    }

    fun retry() = refresh()

    private fun refresh() {
        viewModelScope.launch {
            isLoadingFlow.value = true
            errorMessageFlow.value = null

            try {
                repository.refreshRockets()
                errorMessageFlow.value = null
            } catch (e: Exception) {

                val message = when (e) {
                    is java.net.UnknownHostException,
                    is java.net.SocketTimeoutException -> "Sin conexión. Revisa tu red e inténtalo de nuevo."
                    else -> "Error inesperado al cargar. Inténtalo de nuevo."
                }

                val hasCache = uiState.value.rockets.isNotEmpty()

                if (hasCache) {
                    _uiEvent.tryEmit(HomeUiEvent.ShowSnackbar("Sin conexión. Mostrando datos guardados."))
                    errorMessageFlow.value = null
                } else {
                    errorMessageFlow.value = message
                }

            } finally {
                isLoadingFlow.value = false
            }

        }
    }
}

private fun Rocket.toUi(): RocketItemUi =
    RocketItemUi(
        id = id,
        name = name,
        imageUrl = imageUrl
    )
