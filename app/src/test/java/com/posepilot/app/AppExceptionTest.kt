package com.posepilot.app

import com.posepilot.app.core.errors.AppException
import com.posepilot.app.core.errors.userMessage
import org.junit.Assert.assertEquals
import org.junit.Test

class AppExceptionTest {

    @Test
    fun `permission denied message includes permission`() {
        val exception = AppException.PermissionDenied("android.permission.CAMERA")
        assertEquals("Permission denied: android.permission.CAMERA", exception.message)
    }

    @Test
    fun `camera unavailable message is human readable`() {
        assertEquals(
            "Camera is unavailable on this device.",
            AppException.CameraUnavailable().message,
        )
    }

    @Test
    fun `computer vision message includes reason`() {
        val exception = AppException.ComputerVision("no person detected")
        assertEquals(
            "Pose analysis failed: no person detected",
            exception.message,
        )
    }

    @Test
    fun `generic exception keeps custom message`() {
        assertEquals("Storage full", AppException.Generic("Storage full").message)
    }

    @Test
    fun `userMessage maps unknown throwables to fallback`() {
        assertEquals("Something went wrong.", IllegalStateException("boom").userMessage())
        assertEquals("Storage full", AppException.Generic("Storage full").userMessage())
    }
}
