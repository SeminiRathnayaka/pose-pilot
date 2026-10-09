package com.posepilot.app

import com.posepilot.app.shared.data.SamplePoses
import com.posepilot.app.shared.model.PoseCategory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SamplePosesTest {

    @Test
    fun `sample catalog contains poses for every category`() {
        val coveredCategories = SamplePoses.all.map { it.category }.toSet()
        assertEquals(PoseCategory.entries.toSet(), coveredCategories)
    }

    @Test
    fun `every pose has a description and at least three instructions`() {
        SamplePoses.all.forEach { pose ->
            assertTrue("missing description for ${pose.id}", pose.description.isNotBlank())
            assertTrue("too few instructions for ${pose.id}", pose.instructions.size >= 3)
            assertTrue(
                "blank instruction for ${pose.id}",
                pose.instructions.none { it.isBlank() },
            )
        }
    }

    @Test
    fun `popular poses are the four featured ones`() {
        assertEquals(
            listOf("casual_standing", "walking_shot", "relaxed_sitting", "street_style"),
            SamplePoses.popular.map { it.id },
        )
    }

    @Test
    fun `byId returns matching pose and null for unknown`() {
        assertEquals("Relaxed Standing", SamplePoses.byId("relaxed_standing")?.name)
        assertEquals(4, SamplePoses.byId("relaxed_standing")?.instructions?.size)
        assertNull(SamplePoses.byId("missing"))
    }

    @Test
    fun `search filters by query and category`() {
        val walking = SamplePoses.search(query = "walk", categoryKey = "all")
        assertTrue(walking.any { it.id == "walking_shot" })

        val sitting = SamplePoses.search(query = "", categoryKey = "sitting")
        assertTrue(sitting.all { it.category.key == "sitting" })
        assertTrue(sitting.isNotEmpty())

        val none = SamplePoses.search(query = "zzzzz", categoryKey = "all")
        assertTrue(none.isEmpty())
    }

    @Test
    fun `category keys start with all filter`() {
        assertEquals(
            listOf("all") + PoseCategory.entries.map { it.key },
            SamplePoses.categoryKeys,
        )
    }
}
