package com.mashup.api.fcm.response

data class FcmRegistResponse(
    val active: Boolean,
    val applicationId: String,
    val cloudMessageType: String,
    val dateCreated: String,
    val deviceId: Int,
    val id: Int,
    val name: String,
    val registrationId: String
)