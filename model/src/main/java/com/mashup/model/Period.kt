package com.mashup.model

import android.os.Parcel
import android.os.Parcelable

data class Period(
        val pk: Int,
        val isCurrent: Boolean,
        val number: Int
) : Parcelable {
        constructor(source: Parcel) : this(
                source.readInt(),
                1 == source.readInt(),
                source.readInt()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
                writeInt(pk)
                writeInt((if (isCurrent) 1 else 0))
                writeInt(number)
        }

        companion object {
                @JvmField
                val CREATOR: Parcelable.Creator<Period> = object : Parcelable.Creator<Period> {
                        override fun createFromParcel(source: Parcel): Period = Period(source)
                        override fun newArray(size: Int): Array<Period?> = arrayOfNulls(size)
                }
        }
}
