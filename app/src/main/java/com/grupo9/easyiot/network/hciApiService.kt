package com.grupo9.easyiot.network

import com.grupo9.easyiot.model.device.Devices
import com.grupo9.easyiot.model.routines.Routines
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import kotlinx.serialization.json.Json
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val BASE_URL = "http://localhost:8080/api/routines"

private val httpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)
private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor).build()

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL).client(okHttpClient).build()
/*
private val retrofitroutines = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl("$BASE_URL/routines").client(okHttpClient).build()
*/
interface DeviceApiService {
    @GET("api")
    suspend fun getDeviceList () : Devices
}

object DeviceApi {
    val retorfitService : DeviceApiService by lazy {
        retrofit.create(DeviceApiService::class.java)
    }
}
interface RoutineApiService {
    @GET("api")
    suspend fun getRoutineList() : Routines
}

object RoutineApi {
    val retorfitService : RoutineApiService by lazy {
        retrofit.create(RoutineApiService::class.java)
    }
}

