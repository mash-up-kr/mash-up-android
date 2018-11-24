package com.mashup.model

import org.threeten.bp.LocalDateTime
import java.io.Serializable


data class Address(
    val location: String,
    val latitude: Double?,
    val longitude: Double?
)

data class Notice (
    val id: Int,
    val title: String,
    val description: String,
    val address: Address,
    val date: LocalDateTime,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
): Serializable