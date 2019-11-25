package com.mashup.api

data class ErrorResponse(
    val code: String,
    val message: String?
) {
    fun hasErrors(): Boolean {
        return  !message.isNullOrEmpty()
    }
}