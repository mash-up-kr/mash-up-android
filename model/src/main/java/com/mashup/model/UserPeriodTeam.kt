package com.mashup.model

import android.os.Parcel
import android.os.Parcelable

data class UserPeriodTeam(
        val period: Period,
        val team: Team
) : Parcelable {
        constructor(source: Parcel) : this(
                source.readParcelable<Period>(Period::class.java.classLoader),
                source.readParcelable<Team>(Team::class.java.classLoader)
        )

        override fun describeContents() = 0

        override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
                writeParcelable(period, 0)
                writeParcelable(team, 0)
        }

        companion object {
                @JvmField
                val CREATOR: Parcelable.Creator<UserPeriodTeam> =
                        object : Parcelable.Creator<UserPeriodTeam> {
                                override fun createFromParcel(source: Parcel): UserPeriodTeam =
                                        UserPeriodTeam(source)

                                override fun newArray(size: Int): Array<UserPeriodTeam?> =
                                        arrayOfNulls(size)
                        }
        }
}
