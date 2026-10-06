package com.posepilot.app.core.errors

sealed class AppException(
    message: String,
    cause: Throwable? = null,
) : Exception(message, cause) {

    class PermissionDenied(
        val permission: String,
        cause: Throwable? = null,
    ) : AppException("Permission denied: $permission", cause)

    class CameraUnavailable(cause: Throwable? = null) :
        AppException("Camera is unavailable on this device.", cause)

    class StorageUnavailable(cause: Throwable? = null) :
        AppException("Storage is unavailable.", cause)

    class ComputerVision(val reason: String, cause: Throwable? = null) :
        AppException("Pose analysis failed: $reason", cause)

    class Generic(override val message: String, cause: Throwable? = null) :
        AppException(message, cause)
}

fun Throwable.userMessage(): String = when (this) {
    is AppException -> message ?: "Something went wrong."
    else -> "Something went wrong."
}
