package com.mashup.api.fcm.request

data class FcmRegistRequest(
    val registrationId: String,
    val active: Boolean = true,
    val id: Int? = null,
    val name: String? = null,
    val applicationId: String? = null,
    val cloudMessageType: String = "FCM",
    val deviceId: Int? = null
)