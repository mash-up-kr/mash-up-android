package com.mashup.model

import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime
import java.io.Serializable


data class Location (
    @SerializedName("location") val address: String,
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?
)

data class Notice (
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("location") val location: Location,
    @SerializedName("meeting_at") val date: LocalDateTime,
    @SerializedName("noticed_at") val noticedAt: LocalDateTime
): Serializable