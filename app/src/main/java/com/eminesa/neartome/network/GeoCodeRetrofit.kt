package com.eminesa.neartome.network

import com.eminesa.neartome.request.DirectionsRequest
import com.eminesa.neartome.response.DirectionsResponse
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import retrofit2.Call
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

class DirectionsRetrofit {
    private val baseUrl = "https://mapapi.cloud.huawei.com/mapApi/v1/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(setInterceptors())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <S> createService(serviceClass: Class<S>?): S? {
        return serviceClass?.let { retrofit.create(it) }
    }

    private fun setInterceptors(): OkHttpClient {
        val logger = HttpLoggingInterceptor()
        logger.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val url: HttpUrl = chain.request().url.newBuilder().build()
                val request = chain.request().newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .url(url)
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logger)
            .build()
    }
}

open class DirectionsBaseRepo {
    private var directionsApis: DirectionsInterface? = null

    fun getInstance(): DirectionsInterface? {
        if (directionsApis == null)
            setMainApis()
        return directionsApis
    }

    private fun setMainApis() {
        directionsApis = DirectionsRetrofit().createService(DirectionsInterface::class.java)
    }
}

interface DirectionsInterface {
    @POST("routeService/{type}")
    fun getDirectionsWithType(
        @Path(value = "type", encoded = true) type: String,
        @Body directionsRequest: DirectionsRequest
    ): Call<DirectionsResponse>

    @POST("routeService/driving")
    fun getPipeLine(
        @Query("key") key: String? = "DAEDAOPYUdPj7qvHfqJQFbNElDN12UMUGOCv6dl4F54eOONr5x9QUlXQh9H81Fmu9hpKGmPz89EzFViggby52iXI4KYVU6hEalBBDQ==",
        @Body directionsRequest: DirectionsRequest?
    ): Call<DirectionsResponse>

}