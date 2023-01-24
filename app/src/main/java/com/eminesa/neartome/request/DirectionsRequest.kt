package com.eminesa.neartome.request

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class DirectionsRequest(
    @field:Json(name = "origin")
    val origin: LatLngData?,

    @field:Json(name = "destination")
    val destination: LatLngData?

) : Parcelable

@Parcelize
data class LatLngData(
    @field:Json(name = "lat")
    val lat: Double,

    @field:Json(name = "lng")
    val lng: Double

) : Parcelable