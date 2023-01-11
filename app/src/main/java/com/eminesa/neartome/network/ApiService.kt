package com.eminesa.neartome.network

import com.eminesa.neartome.request.NearByRequest
import com.eminesa.neartome.response.NearByResponse
import com.eminesa.neartome.response.PharmacyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST("map/nearest")
    suspend fun getNearBy(@Body nearByRequest: NearByRequest?): NearByResponse


    @GET("health/dutyPharmacy")
    suspend fun getPharmacy(@Query("il") il: String?, @Query("ilce") ilce: String?): PharmacyResponse

}