package com.springsamurais.toyswap.model

import android.os.Parcel
import android.os.Parcelable

class Listing(
    var id: Long? = null,
    var title: String? = null,
    var datePosted: String? = null,
    var category: String? = null,
    var description: String? = null,
    var condition: String? = null,
    var statusListing: String? = null,
    var member: Member? = null,
    var images: Array<Image>? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Member::class.java.classLoader),
        parcel.createTypedArray(Image)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(datePosted)
        parcel.writeString(category)
        parcel.writeString(description)
        parcel.writeString(condition)
        parcel.writeString(statusListing)
        parcel.writeParcelable(member, flags)
        parcel.writeTypedArray(images, flags)
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

