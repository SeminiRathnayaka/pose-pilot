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
        assertEquals(PoseMode.FREE, PoseMode.fromKey("free"))
    }

    @Test
    fun `fromKey falls back to solo for unknown key`() {
        assertEquals(PoseMode.SOLO, PoseMode.fromKey("unknown"))
        assertEquals(PoseMode.SOLO, PoseMode.fromKey(null))
    }

    @Test
    fun `entries cover exactly four modes`() {
        assertEquals(4, PoseMode.entries.size)
    }
}
