package com.eminesa.neartome.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import retrofit2.http.Field

@Parcelize
data class NearByResponse(

    @field:Json(name = "success")
    val success: String? = null,

    @field:Json(name = "result")
    val result: List<ResultNearBy>? = null,

    ) : Parcelable

@Parcelize
data class ResultNearBy(

    @field:Json(name = "name")
    val name: String? = null,

    @field:Json(name = "address")
    val address: String? = null,

    @field:Json(name = "map")
    val map: String? = null,

    @field:Json(name = "url")
    val url: String? = null,

    ) : Parcelable
