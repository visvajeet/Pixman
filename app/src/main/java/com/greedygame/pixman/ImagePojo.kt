package com.greedygame.pixman

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImagePojo(val id:Long, val name:String, val path:String) : Parcelable