package com.mashup.app.attendees

enum class ItemType(val typeInt: Int) {
    NORMAL(0),
    SECTION(1)
}

data class AttendeesItem(val type: ItemType, val item: Any)
