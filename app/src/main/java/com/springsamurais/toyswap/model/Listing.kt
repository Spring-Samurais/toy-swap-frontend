package com.springsamurais.toyswap.model

import android.os.Parcel
import android.os.Parcelable

class Listing(
    var id: Long? = null,
    var title: String? = null,
    var photo: String? = null,
    var member: Member? = null,
    var datePosted: String? = null,
    var category: String? = null,
    var description: String? = null,
    var condition: String? = null,
    var statusListing: String? = null,
//    var comments: List<Comment>? = null
) : Parcelable {

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Long::class.java.classLoader) as? Long
        title = parcel.readString()
        photo = parcel.readString()
        datePosted = parcel.readString()
        category = parcel.readString()
        description = parcel.readString()
        condition = parcel.readString()
        statusListing = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(photo)
        parcel.writeString(datePosted)
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeString(condition)
        parcel.writeString(statusListing)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Listing> {
        override fun createFromParcel(parcel: Parcel): Listing {
            return Listing(parcel)
        }

        override fun newArray(size: Int): Array<Listing?> {
            return arrayOfNulls(size)
        }
    }


}
