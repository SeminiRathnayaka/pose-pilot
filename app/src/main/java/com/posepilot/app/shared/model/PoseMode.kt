package com.posepilot.app.shared.model

import androidx.annotation.StringRes
import com.posepilot.app.R

enum class PoseMode(
    @StringRes val labelRes: Int,
    val key: String,
) {
    SOLO(R.string.pose_mode_solo, "solo"),
    COUPLE(R.string.pose_mode_couple, "couple"),
    GROUP(R.string.pose_mode_group, "group"),
    ;

    companion object {
        fun fromKey(key: String?): PoseMode =
            entries.firstOrNull { it.key == key } ?: SOLO
    }
}
