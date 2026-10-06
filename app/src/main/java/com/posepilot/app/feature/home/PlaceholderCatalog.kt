package com.posepilot.app.feature.home

import com.posepilot.app.shared.model.Pose
import com.posepilot.app.shared.model.PoseMode

object PlaceholderCatalog {

    val poses: List<Pose> = listOf(
        Pose(
            id = "solo_stand_tall",
            name = "Stand tall",
            mode = PoseMode.SOLO,
            description = "Weight on your back foot, shoulders relaxed.",
        ),
        Pose(
            id = "solo_lean_in",
            name = "Lean in",
            mode = PoseMode.SOLO,
            description = "Slight forward lean with hands loose.",
        ),
        Pose(
            id = "solo_profile",
            name = "Clean profile",
            mode = PoseMode.SOLO,
            description = "Turn three quarters, chin slightly down.",
        ),
        Pose(
            id = "couple_face_to_face",
            name = "Face to face",
            mode = PoseMode.COUPLE,
            description = "Stand close, foreheads almost touching.",
        ),
        Pose(
            id = "couple_walk",
            name = "Walking step",
            mode = PoseMode.COUPLE,
            description = "Mid-stride together, hands linked.",
        ),
        Pose(
            id = "group_line",
            name = "Even line",
            mode = PoseMode.GROUP,
            description = "Shoulders aligned, everyone same depth.",
        ),
        Pose(
            id = "group_stagger",
            name = "Staggered row",
            mode = PoseMode.GROUP,
            description = "Two rows, heads visible between gaps.",
        ),
    )

    fun forMode(mode: PoseMode): List<Pose> = poses.filter { it.mode == mode }
}
