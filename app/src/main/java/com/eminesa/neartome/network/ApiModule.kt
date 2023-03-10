package com.eminesa.dailyofspace.network

import androidx.viewbinding.BuildConfig
import com.eminesa.neartome.network.ApiService
import com.eminesa.neartome.network.Repository
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import okhttp3.Interceptor

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    //{"copyright":"Jonathan Lodge","date":"2021-11-29","explanation":"What created the strange spiral structure on the upper left?  No one is sure, although it is likely related to a star in a binary star system entering the planetary nebula phase, when its outer atmosphere is ejected.  The huge spiral spans about a third of a light year across and, winding four or five complete turns, has a regularity that is without precedent.  Given the expansion rate of the spiral gas, a new layer must appear about every 800 years, a close match to the time it takes for the two stars to orbit each other. The star system that created it is most commonly known as LL Pegasi, but also AFGL 3068 and IRAS 23166+1655.  The featured image was taken in near-infrared light by the Hubble Space Telescope. Why the spiral glows is itself a mystery, with a leading hypothesis being illumination by light reflected from nearby stars.","hdurl":"https://apod.nasa.gov/apod/image/2111/LLPegasi_HubbleLodge_1926.jpg","media_type":"image","service_version":"v1","title":"The Extraordinary Spiral in LL Pegasi","url":"https://apod.nasa.gov/apod/image/2111/LLPegasi_HubbleLodge_960.jpg"}
    //https://api.nasa.gov/planetary/apod?api_key=da2QThPK0PiunpjTKv6NUE67Od0L8E78ntl3GuOR
    // val BASE_URL = "https://api.nasa.gov/planetary/"
    val BASE_URL = "https://www.nosyapi.com/"

    /**
     * Bu fonksiyon yap??land??r??lm???? de??erler ile CertificatePinner instance olu??turur ve d??nd??r??r.
     * @return CertificatePinner
     */
    @Singleton
    @Provides
    fun certificatePinner() = CertificatePinner.Builder()
        .add("", "")// istenen SHA eklenir
        .build()

    /**
     * Bu fonksiyon yap??land??r??lm???? de??erler ile OkHttpClient instance olu??turur ve d??nd??r??r.
     * @param interceptor DynamicInterceptor
     * @return OkHttpClient
     */
    private val interceptor = Interceptor {
        val request = it.request()
        val requestBuilder = request.newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader(
                "Authorization",
                "Bearer 6N148toByrhc0U6bBIG0txdVQEhGfDQByj5Aj0zynm6eoAaHaihlZ6R3oqwi"
            )

        return@Interceptor it.proceed(requestBuilder.build())
    }

    @Singleton
    @Provides
    fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG)
                    HttpLoggingInterceptor.Level.BODY
                else
                    HttpLoggingInterceptor.Level.NONE
            })
            .addInterceptor(interceptor)
            .build()
    }

    /**
     * Bu fonksiyon yap??land??r??lm???? de??erler ile Retrofit instance olu??turur ve d??nd??r??r.
     * @param okHttpClient request olu??turmak i??in kullan??l??r.
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    /**
     * Bu fonksiyon yap??land??r??lm???? de??erler ile ApiService instance olu??turur ve d??nd??r??r.
     * @param retrofit ApiService instance olu??turmak i??in kullan??l??r.
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Bu fonksiyon yap??land??r??lm???? de??erler ile Repository instance olu??turur ve d??nd??r??r.
     * @param apiService Repository parametresi
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService) = Repository(apiService)
}