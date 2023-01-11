package com.eminesa.neartome.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PharmacyResponse(

    @field:Json(name = "success")
    val success: Boolean? = null,

    @field:Json(name = "result")
    val result: List<Pharmacy>? = null,

    ) : Parcelable

@Parcelize
data class Pharmacy(

    @field:Json(name = "name")
    val name: String? = null,

    @field:Json(name = "dist")
    val dist: String? = null,

    @field:Json(name = "address")
    val address: String? = null,

    @field:Json(name = "phone")
    val phone: String? = null,

    @field:Json(name = "loc")
    val loc: String? = null,

    ) : Parcelable

