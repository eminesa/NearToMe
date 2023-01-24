package com.eminesa.neartome.network

 import dailyofspace.eminesa.dailyofspace.network.PharmacyResponse
 import retrofit2.http.GET
 import retrofit2.http.Query

interface ApiService {

    @GET("apiv2/pharmacy")
    suspend fun getPharmacy(
        @Query("city") city: String? = "istanbul",
        @Query("county") county: String? = "avcilar"
    ): PharmacyResponse

}