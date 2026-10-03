package com.unit.triviaapp.ui.states

import com.unit.triviaapp.models.Platforms

sealed interface PlatformsUiState {
    data object Loading: PlatformsUiState

    data class Success(
        val platforms: List<Platforms>
    ): PlatformsUiState

    data class Error(
        val message: String
    ): PlatformsUiState
}