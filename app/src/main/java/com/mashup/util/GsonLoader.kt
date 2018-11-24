package com.mashup.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetTime
import org.threeten.bp.format.DateTimeFormatter

object GsonLoader {
    val gson: Gson

    init {
        gson = GsonBuilder()
            .setPrettyPrinting()
            .registerTypeAdapter(LocalDateTime::class.java, JsonSerializer<LocalDateTime> { src, _, _ ->
                JsonPrimitive(src?.atOffset(OffsetTime.now().offset)?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
            })
            .registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer<LocalDateTime> { src, _, _ ->
                val datetime = LocalDateTime.parse(src.asJsonPrimitive.asString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                datetime.plusSeconds(OffsetTime.now().offset.totalSeconds.toLong())
            })
            .create()
    }
}