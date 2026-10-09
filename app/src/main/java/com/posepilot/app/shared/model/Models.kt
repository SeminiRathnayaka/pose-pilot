package com.posepilot.app.shared.model

import androidx.annotation.StringRes
import com.posepilot.app.R

enum class PoseMode(
    @param:StringRes val labelRes: Int,
    val key: String,
) {
    SOLO(R.string.pose_mode_solo, "solo"),
    COUPLE(R.string.pose_mode_couple, "couple"),
    GROUP(R.string.pose_mode_group, "group"),
    FREE(R.string.pose_mode_free, "free"),
    ;

    companion object {
        fun fromKey(key: String?): PoseMode =
            entries.firstOrNull { it.key == key } ?: SOLO
    }
}

enum class PoseCategory(
    @param:StringRes val labelRes: Int,
    val key: String,
) {
    CASUAL(R.string.category_casual, "casual"),
    FASHION(R.string.category_fashion, "fashion"),
    STREET(R.string.category_street, "street"),
    SITTING(R.string.category_sitting, "sitting"),
    STANDING(R.string.category_standing, "standing"),
    WALKING(R.string.category_walking, "walking"),
    PROFESSIONAL(R.string.category_professional, "professional"),
    GRADUATION(R.string.category_graduation, "graduation"),
    INSTAGRAM(R.string.category_instagram, "instagram"),
    OUTDOOR(R.string.category_outdoor, "outdoor"),
}

enum class PoseDifficulty(
    @param:StringRes val labelRes: Int,
) {
    EASY(R.string.difficulty_easy),
    MEDIUM(R.string.difficulty_medium),
    HARD(R.string.difficulty_hard),
}

data class Pose(
    val id: String,
    val name: String,
    val mode: PoseMode,
    val category: PoseCategory,
    val difficulty: PoseDifficulty,
    val description: String,
    val instructions: List<String>,
    val isPopular: Boolean = false,
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
