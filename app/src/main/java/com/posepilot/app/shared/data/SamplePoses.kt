package com.posepilot.app.shared.data

import com.posepilot.app.shared.model.Pose
import com.posepilot.app.shared.model.PoseCategory
import com.posepilot.app.shared.model.PoseDifficulty
import com.posepilot.app.shared.model.PoseMode

object SamplePoses {

    val all: List<Pose> = listOf(
        Pose(
            id = "casual_standing",
            name = "Casual Standing",
            mode = PoseMode.SOLO,
            category = PoseCategory.CASUAL,
            difficulty = PoseDifficulty.EASY,
            description = "A relaxed, everyday stance that works almost anywhere.",
            instructions = listOf(
                "Shift your weight onto your back leg.",
                "Rest your hands in your pockets or let them hang loose.",
                "Keep your gaze slightly above the lens.",
                "Tilt your chin down a touch for a stronger jawline.",
            ),
            isPopular = true,
        ),
        Pose(
            id = "relaxed_standing",
            name = "Relaxed Standing",
            mode = PoseMode.SOLO,
            category = PoseCategory.STANDING,
            difficulty = PoseDifficulty.EASY,
            description = "The simplest natural standing pose, great for beginners.",
            instructions = listOf(
                "Stand comfortably with your weight on one leg.",
                "Keep your shoulders relaxed.",
                "Turn your body slightly instead of facing the camera straight on.",
                "Keep your hands natural and relaxed.",
            ),
        ),
        Pose(
            id = "walking_shot",
            name = "Walking Shot",
            mode = PoseMode.SOLO,
            category = PoseCategory.WALKING,
            difficulty = PoseDifficulty.MEDIUM,
            description = "Movement makes a photo feel candid and alive.",
            instructions = listOf(
                "Walk slowly toward or across the frame.",
                "Look ahead or slightly off-camera, not at the lens.",
                "Keep one foot mid-stride when the photo is taken.",
                "Let your arms swing naturally.",
            ),
            isPopular = true,
        ),
        Pose(
            id = "relaxed_sitting",
            name = "Relaxed Sitting",
            mode = PoseMode.SOLO,
            category = PoseCategory.SITTING,
            difficulty = PoseDifficulty.EASY,
            description = "A calm seated pose with clean lines and easy posture.",
            instructions = listOf(
                "Sit near the edge of the seat, not deep into it.",
                "Cross your ankles instead of your knees.",
                "Lean slightly forward toward the camera.",
                "Rest your hands loosely on your lap or knee.",
            ),
            isPopular = true,
        ),
        Pose(
            id = "street_style",
            name = "Street Style",
            mode = PoseMode.SOLO,
            category = PoseCategory.STREET,
            difficulty = PoseDifficulty.MEDIUM,
            description = "Editorial energy for city backgrounds and textured walls.",
            instructions = listOf(
                "Lean one shoulder against a wall or railing.",
                "Put one foot forward with the toe pointed out.",
                "Keep your chin slightly down and eyes to the side.",
                "Hold your jacket or bag for a natural hand placement.",
            ),
            isPopular = true,
        ),
        Pose(
            id = "over_shoulder",
            name = "Over the Shoulder",
            mode = PoseMode.SOLO,
            category = PoseCategory.FASHION,
            difficulty = PoseDifficulty.MEDIUM,
            description = "A classic fashion look with a glance back at the camera.",
            instructions = listOf(
                "Stand with your back partly toward the camera.",
                "Turn only your head and shoulders back.",
                "Soften your eyes and relax your jaw.",
                "Keep your hands out of frame or resting at your side.",
            ),
        ),
        Pose(
            id = "cafe_lean",
            name = "Cafe Lean",
            mode = PoseMode.SOLO,
            category = PoseCategory.CASUAL,
            difficulty = PoseDifficulty.EASY,
            description = "Perfect for coffee shops, counters, and window light.",
            instructions = listOf(
                "Lean your forearms on the counter or table.",
                "Keep your elbows relaxed, not locked.",
                "Shift your weight onto one hip.",
                "Look out the window or just past the camera.",
            ),
        ),
        Pose(
            id = "team_lineup",
            name = "Team Line-Up",
            mode = PoseMode.GROUP,
            category = PoseCategory.PROFESSIONAL,
            difficulty = PoseDifficulty.EASY,
            description = "A clean, aligned group shot where everyone is visible.",
            instructions = listOf(
                "Stand in one line with shoulders slightly overlapped.",
                "Everyone angles their body a few degrees inward.",
                "Keep heads at staggered heights if possible.",
                "Match expressions: all smile or all serious.",
            ),
        ),
        Pose(
            id = "graduation_cap_toss",
            name = "Cap Toss",
            mode = PoseMode.GROUP,
            category = PoseCategory.GRADUATION,
            difficulty = PoseDifficulty.MEDIUM,
            description = "The celebratory graduation moment, timed together.",
            instructions = listOf(
                "Stand in a loose row with space between people.",
                "Hold caps at chest height before the toss.",
                "Toss on a counted three and look up.",
                "Keep gowns open and posture tall.",
            ),
        ),
        Pose(
            id = "insta_mirror",
            name = "Mirror Frame",
            mode = PoseMode.SOLO,
            category = PoseCategory.INSTAGRAM,
            difficulty = PoseDifficulty.EASY,
            description = "A clean mirror shot with balanced framing.",
            instructions = listOf(
                "Stand centered so the mirror frames your full outfit.",
                "Hold the phone at chest height, not face height.",
                "Place one foot forward to elongate your stance.",
                "Keep the background tidy and uncluttered.",
            ),
        ),
        Pose(
            id = "park_bench",
            name = "Park Bench",
            mode = PoseMode.SOLO,
            category = PoseCategory.OUTDOOR,
            difficulty = PoseDifficulty.EASY,
            description = "A friendly outdoor pose with natural surroundings.",
            instructions = listOf(
                "Sit sideways on the bench for a longer line.",
                "Drape one arm along the backrest.",
                "Cross your legs at the ankle.",
                "Look toward the light, not straight at the lens.",
            ),
        ),
        Pose(
            id = "runway_walk",
            name = "Runway Strut",
            mode = PoseMode.SOLO,
            category = PoseCategory.FASHION,
            difficulty = PoseDifficulty.HARD,
            description = "A confident mid-walk pose for full-body fashion shots.",
            instructions = listOf(
                "Step forward with a straight, leading leg.",
                "Keep your hips square and shoulders back.",
                "Freeze mid-step while the shutter fires.",
                "Point your chin slightly past the camera.",
            ),
        ),
        Pose(
            id = "wall_lean",
            name = "Wall Lean",
            mode = PoseMode.SOLO,
            category = PoseCategory.STREET,
            difficulty = PoseDifficulty.EASY,
            description = "An easy go-to for textured urban backgrounds.",
            instructions = listOf(
                "Press your back lightly against the wall.",
                "Bend one knee and keep the other leg straight.",
                "Drop one shoulder toward the wall.",
                "Let your gaze wander off-camera.",
            ),
        ),
        Pose(
            id = "forehead_touch",
            name = "Forehead Touch",
            mode = PoseMode.COUPLE,
            category = PoseCategory.CASUAL,
            difficulty = PoseDifficulty.EASY,
            description = "A close, natural couple moment without looking staged.",
            instructions = listOf(
                "Stand chest to chest at a slight angle.",
                "Lower your foreheads until they almost touch.",
                "Close your eyes or look down together.",
                "Keep hands linked or resting on each other's arms.",
            ),
        ),
        Pose(
            id = "group_jump",
            name = "Group Jump",
            mode = PoseMode.GROUP,
            category = PoseCategory.INSTAGRAM,
            difficulty = PoseDifficulty.MEDIUM,
            description = "High-energy group shot that always feels fun.",
            instructions = listOf(
                "Stand in a row with room to move.",
                "Bend your knees together on the count of three.",
                "Jump straight up and kick heels back.",
                "Laugh naturally instead of forcing expressions.",
            ),
        ),
    )

    val popular: List<Pose> = all.filter { it.isPopular }

    val categoryKeys: List<String> = listOf("all") + PoseCategory.entries.map { it.key }

    fun byId(id: String): Pose? = all.firstOrNull { it.id == id }

    fun search(query: String, categoryKey: String): List<Pose> {
        val trimmed = query.trim()
        return all.filter { pose ->
            val categoryMatches = categoryKey == "all" || pose.category.key == categoryKey
            val queryMatches = trimmed.isEmpty() ||
                pose.name.contains(trimmed, ignoreCase = true) ||
                pose.category.name.contains(trimmed, ignoreCase = true) ||
                pose.description.contains(trimmed, ignoreCase = true)
            categoryMatches && queryMatches
        }
    }
}
