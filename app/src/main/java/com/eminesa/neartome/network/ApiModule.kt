package com.eminesa.neartome.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.liulishuo.okdownload.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
  //apikey 3Jt2A7Sr29ooeMzAiMOGxE:7iYP3N2yVjjyxqbKbCPl9o
  private const val BASE_URL = "https://api.collectapi.com/"

    /**
     * Bu fonksiyon yapılandırılmış değerler ile OkHttpClient instance oluşturur ve döndürür.
     * @param interceptor DynamicInterceptor
     * @return OkHttpClient
     */
    //  .header("content-type", "application/json")
    //  .header("authorization", "apikey 3Jt2A7Sr29ooeMzAiMOGxE:7iYP3N2yVjjyxqbKbCPl9o")
    private val interceptor = Interceptor{
        val request = it.request()
        val requestBuilder = request.newBuilder()
            .addHeader("content-type", "application/json")
            .addHeader("authorization", "apikey 3Jt2A7Sr29ooeMzAiMOGxE:7iYP3N2yVjjyxqbKbCPl9o")

        return@Interceptor it.proceed(requestBuilder.build())
    }

    @Singleton
    @Provides
    fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(45, TimeUnit.SECONDS)
            .connectTimeout(45, TimeUnit.SECONDS)
            .writeTimeout(45, TimeUnit.SECONDS)
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
     * Bu fonksiyon yapılandırılmış değerler ile Retrofit instance oluşturur ve döndürür.
     * @param okHttpClient request oluşturmak için kullanılır.
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
     * Bu fonksiyon yapılandırılmış değerler ile ApiService instance oluşturur ve döndürür.
     * @param retrofit ApiService instance oluşturmak için kullanılır.
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun getApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Bu fonksiyon yapılandırılmış değerler ile Repository instance oluşturur ve döndürür.
     * @param apiService Repository parametresi
     * @return Retrofit
     */
    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService) = Repository(apiService)
}