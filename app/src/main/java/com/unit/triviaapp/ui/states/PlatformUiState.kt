package com.unit.triviaapp.ui.states

import com.unit.triviaapp.models.Platforms

sealed interface PlatformUiState {
    data object Loading: PlatformUiState

    data class Success(
        val platforms: List<Platforms>
    ): PlatformUiState

    data class Error(
        val message: String
    ): PlatformUiState
}