package com.posepilot.app

import com.posepilot.app.shared.model.PoseMode
import org.junit.Assert.assertEquals
import org.junit.Test

class PoseModeTest {

    @Test
    fun `fromKey returns matching mode`() {
        assertEquals(PoseMode.SOLO, PoseMode.fromKey("solo"))
        assertEquals(PoseMode.COUPLE, PoseMode.fromKey("couple"))
        assertEquals(PoseMode.GROUP, PoseMode.fromKey("group"))
    }

    @Test
    fun `fromKey falls back to solo for unknown key`() {
        assertEquals(PoseMode.SOLO, PoseMode.fromKey("unknown"))
        assertEquals(PoseMode.SOLO, PoseMode.fromKey(null))
    }

    @Test
    fun `entries cover exactly three modes`() {
        assertEquals(3, PoseMode.entries.size)
    }
}
