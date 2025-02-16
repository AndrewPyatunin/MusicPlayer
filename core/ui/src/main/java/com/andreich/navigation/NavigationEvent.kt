package com.andreich.navigation

sealed interface NavigationEvent {

    data class NavigateToFeatureMusicPlayer(
        val trackId: String
    ) : NavigationEvent
}