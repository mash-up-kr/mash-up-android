package com.mashup.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

enum class VoteStatus {
    @SerializedName("unselected")
    UNSELECTED,
    @SerializedName("attend")
    ATTEND,
    @SerializedName("absent")
    ABSENT,
    @SerializedName("late")
    LATE,
    NONE
}

data class NoticeAttendance(
    val pk: Int,
    val user: User,
    val vote: VoteStatus,
    val voteDisplay: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readParcelable<User>(User::class.java.classLoader),
        VoteStatus.values()[source.readInt()],
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(pk)
        writeParcelable(user, 0)
        writeInt(vote.ordinal)
        writeString(voteDisplay)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<NoticeAttendance> =
            object : Parcelable.Creator<NoticeAttendance> {
                override fun createFromParcel(source: Parcel): NoticeAttendance =
                    NoticeAttendance(source)

                override fun newArray(size: Int): Array<NoticeAttendance?> = arrayOfNulls(size)
            }
    }
}
