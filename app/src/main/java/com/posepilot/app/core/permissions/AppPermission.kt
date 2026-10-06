package com.posepilot.app.core.permissions

enum class AppPermission(val permission: String) {
    CAMERA(android.Manifest.permission.CAMERA),
    RECORD_AUDIO(android.Manifest.permission.RECORD_AUDIO),
    ;

    companion object {
        val requiredForCapture: List<AppPermission> = listOf(CAMERA)
    }
}

sealed interface PermissionStatus {
    data object Granted : PermissionStatus
    data class Denied(val permission: AppPermission) : PermissionStatus
    data class PermanentlyDenied(val permission: AppPermission) : PermissionStatus
}
