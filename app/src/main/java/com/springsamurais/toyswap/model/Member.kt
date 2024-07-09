package com.springsamurais.toyswap.model

import android.os.Parcel
import android.os.Parcelable

class Member (
    var id: Long? = null,
    var name: String? = null,
    var email: String? = null,
    var username: String? = null,
    var location: String? = null,
    var listings: List<Listing>? = null,
    var password: String? = null,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(Listing),
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(username)
        parcel.writeString(location)
        parcel.writeTypedList(listings)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Member> {
        override fun createFromParcel(parcel: Parcel): Member {
            return Member(parcel)
        }

        override fun newArray(size: Int): Array<Member?> {
            return arrayOfNulls(size)
        }
    }
}