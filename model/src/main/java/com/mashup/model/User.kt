package com.mashup.model

import android.os.Parcel
import android.os.Parcelable

data class User(
    val pk: Int,
    val name: String,
    val phoneNumber: String?,
    val email: String?,
    val github: String,
    val userPeriodTeamSet: ArrayList<UserPeriodTeam>
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.createTypedArrayList(UserPeriodTeam.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(pk)
        writeString(name)
        writeString(phoneNumber)
        writeString(email)
        writeString(github)
        writeTypedList(userPeriodTeamSet)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}
