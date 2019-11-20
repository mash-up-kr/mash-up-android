package com.mashup.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Notice(
    val pk: Int,
    val team: Team?,
    val title: String,
    val author: User,
    val startAt: Date?,
    val duration: String?,
    val address1: String,
    val address2: String,
    val description: String,
    val attendanceSet: ArrayList<NoticeAttendance>,
    var userAttendance: VoteStatus = VoteStatus.NONE
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readParcelable<Team>(Team::class.java.classLoader),
        source.readString(),
        source.readParcelable<User>(User::class.java.classLoader),
        source.readSerializable() as Date,
        source.readString(),
        source.readString(),
        source.readString(),
        source.readString(),
        source.createTypedArrayList(NoticeAttendance.CREATOR),
        VoteStatus.values()[source.readInt()]
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(pk)
        writeParcelable(team, 0)
        writeString(title)
        writeParcelable(author, 0)
        writeSerializable(startAt)
        writeString(duration)
        writeString(address1)
        writeString(address2)
        writeString(description)
        writeTypedList(attendanceSet)
        writeInt(userAttendance.ordinal)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Notice> = object : Parcelable.Creator<Notice> {
            override fun createFromParcel(source: Parcel): Notice = Notice(source)
            override fun newArray(size: Int): Array<Notice?> = arrayOfNulls(size)
        }
    }
}
