package com.mashup.model

import android.os.Parcel
import android.os.Parcelable

data class Team(
        val pk: Int,
        val name: String
) : Parcelable {
        constructor(source: Parcel) : this(
                source.readInt(),
                source.readString()
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
                writeInt(pk)
                writeString(name)
        }

        companion object {
                @JvmField
                val CREATOR: Parcelable.Creator<Team> = object : Parcelable.Creator<Team> {
                        override fun createFromParcel(source: Parcel): Team = Team(source)
                        override fun newArray(size: Int): Array<Team?> = arrayOfNulls(size)
                }
        }
}
