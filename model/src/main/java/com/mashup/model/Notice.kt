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
        var userAttendance: VoteStatus = VoteStatus.NONE,
        var voteAttendanceCount: Int = 0,
        var voteAbsentCount: Int = 0,
        var voteUnselectedCount: Int = 0
) : Parcelable {
    fun vote(voteStatus: VoteStatus) {
        if (userAttendance == VoteStatus.ABSENT) {
            userAttendance = VoteStatus.ATTEND
            voteAbsentCount--
            voteAttendanceCount++
        } else if (userAttendance == VoteStatus.ATTEND) {
            userAttendance = VoteStatus.ABSENT
            voteAbsentCount++
            voteAttendanceCount--
        } else {
            userAttendance = voteStatus
            voteUnselectedCount--
            if (voteStatus == VoteStatus.ATTEND)
                voteAttendanceCount++
            else
                voteAbsentCount++
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readParcelable<Team>(Team::class.java.classLoader),
            source.readString(),
            source.readParcelable<User>(User::class.java.classLoader),
            source.readSerializable() as Date?,
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.createTypedArrayList(NoticeAttendance.CREATOR),
            VoteStatus.values()[source.readInt()],
            source.readInt(),
            source.readInt(),
            source.readInt()
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
        writeInt(voteAttendanceCount)
        writeInt(voteAbsentCount)
        writeInt(voteUnselectedCount)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Notice> = object : Parcelable.Creator<Notice> {
            override fun createFromParcel(source: Parcel): Notice = Notice(source)
            override fun newArray(size: Int): Array<Notice?> = arrayOfNulls(size)
        }
    }
}

fun Notice.mapToPresentation(userId: Int) = apply {
    userAttendance = VoteStatus.NONE
    voteAbsentCount = 0
    voteAttendanceCount = 0
    voteUnselectedCount = 0
    attendanceSet.forEach { attendance ->
        if (attendance.user.pk == userId) {
            userAttendance = attendance.vote
        }
        when (attendance.vote) {
            VoteStatus.ATTEND -> voteAttendanceCount++
            VoteStatus.ABSENT -> voteAbsentCount++
            else -> voteUnselectedCount++
        }
    }
}