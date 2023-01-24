package com.eminesa.neartome.response

import android.os.Parcelable
import com.eminesa.neartome.request.LatLngData
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class DirectionsResponse(
    @field:Json(name = "routes") val routes: List<Routes>?,
    @field:Json(name = "returnCode") val returnCode: String?,
    @field:Json(name = "returnDesc") val returnDesc: String?
) : Parcelable

@Parcelize
data class Routes(
    @field:Json(name = "paths") val paths: List<Paths>?,
    @field:Json(name = "bounds") val bounds: Bounds?
) : Parcelable

@Parcelize
data class Paths(
    @field:Json(name = "duration") val duration: Double,
    @field:Json(name = "durationText") val durationText: String,
    @field:Json(name = "durationInTrafficText") val durationInTrafficText: String,
    @field:Json(name = "durationInTraffic") val durationInTraffic: Double,
    @field:Json(name = "distance") val distance: Double,
    @field:Json(name = "startLocation") val startLocation: LatLngData,
    @field:Json(name = "startAddress") val startAddress: String,
    @field:Json(name = "distanceText") val distanceText: String,
    @field:Json(name = "steps") val steps: List<Steps>,
    @field:Json(name = "endLocation") val endLocation: LatLngData,
    @field:Json(name = "endAddress") val endAddress: String
) : Parcelable

@Parcelize
data class Bounds(
    @field:Json(name = "southwest") val southwest: LatLngData,
    @field:Json(name = "northeast") val northeast: LatLngData
) : Parcelable

@Parcelize
data class Steps(
    @field:Json(name = "duration") val duration: Double,
    @field:Json(name = "orientation") val orientation: Double,
    @field:Json(name = "durationText") val durationText: String,
    @field:Json(name = "distance") val distance: Double,
    @field:Json(name = "startLocation") val startLocation: LatLngData,
    @field:Json(name = "instruction") val instruction: String,
    @field:Json(name = "action") val action: String,
    @field:Json(name = "distanceText") val distanceText: String,
    @field:Json(name = "endLocation") val endLocation: LatLngData,
    @field:Json(name = "polyline") val polyline: List<LatLngData>,
    @field:Json(name = "roadName") val roadName: String
) : Parcelable