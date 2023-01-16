package dailyofspace.eminesa.dailyofspace.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class PharmacyResponse(
    @field:Json(name = "status")
    val status: String? = null,

    @field:Json(name = "message")
    val message: String? = null,

    @field:Json(name = "rowCount")
    val rowCount: String? = null,

    @field:Json(name = "systemTime")
    val systemTime: Int? = null,

    @field:Json(name = "data")
    val data:  List<Pharmacy>? = null,

    ): Parcelable


@Parcelize
data class Pharmacy(
    @field:Json(name = "EczaneAdi")
    val EczaneAdi: String?,

    @field:Json(name = "Adresi")
    val Adresi: String?,

    @field:Json(name = "Semt")
    val Semt: String?,

    @field:Json(name = "YolTarifi")
    val YolTarifi: String?,

    @field:Json(name = "Telefon")
    val Telefon: String?,

    @field:Json(name = "Telefon2")
    val Telefon2: String?,

    @field:Json(name = "Sehir")
    val Sehir: String?,

    @field:Json(name = "ilce")
    val ilce: String?,

    @field:Json(name = "latitude")
    val latitude: Double?,

    @field:Json(name = "longitude")
    val longitude: Double?,

    ): Parcelable