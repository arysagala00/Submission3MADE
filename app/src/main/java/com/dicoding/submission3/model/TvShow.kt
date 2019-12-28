package com.dicoding.submission3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TvShow(var photo:String,var name: String, var description:String, var rating:String,var year: String): Parcelable