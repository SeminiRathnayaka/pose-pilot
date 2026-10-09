package com.posepilot.app.shared.data

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object FavoritesStore {

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteIds: StateFlow<Set<String>> = _favoriteIds.asStateFlow()

    fun isFavorite(poseId: String): Boolean = poseId in _favoriteIds.value

    fun toggle(poseId: String) {
        _favoriteIds.value = _favoriteIds.value.let { current ->
            if (poseId in current) current - poseId else current + poseId
        }
    }
}
