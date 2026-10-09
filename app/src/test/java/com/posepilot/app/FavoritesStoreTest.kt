package com.posepilot.app

import com.posepilot.app.shared.data.FavoritesStore
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FavoritesStoreTest {

    @Test
    fun `toggle adds and removes a pose id`() {
        val poseId = "unit_test_pose"

        FavoritesStore.toggle(poseId)
        assertTrue(FavoritesStore.isFavorite(poseId))
        assertTrue(poseId in FavoritesStore.favoriteIds.value)

        FavoritesStore.toggle(poseId)
        assertFalse(FavoritesStore.isFavorite(poseId))
        assertFalse(poseId in FavoritesStore.favoriteIds.value)
    }

    @Test
    fun `unknown pose is not favorite`() {
        assertFalse(FavoritesStore.isFavorite("never_toggled_pose"))
    }
}
