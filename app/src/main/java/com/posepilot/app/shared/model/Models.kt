package com.posepilot.app.shared.model

data class Pose(
    val id: String,
    val name: String,
    val mode: PoseMode,
    val description: String,
    val isPlaceholder: Boolean = true,
)

data class Photo(
    val id: String,
    val uri: String,
    val createdAtMillis: Long,
)

data class Session(
    val id: String,
    val startedAtMillis: Long,
    val mode: PoseMode,
    val photos: List<Photo> = emptyList(),
    val bestScore: Int? = null,
)

data class Favorite(
    val id: String,
    val poseId: String,
    val createdAtMillis: Long,
)

data class UserSettings(
    val name: String = "",
    val mirrorFrontCamera: Boolean = true,
    val saveOriginalPhotos: Boolean = true,
    val soundEffectsEnabled: Boolean = true,
)
