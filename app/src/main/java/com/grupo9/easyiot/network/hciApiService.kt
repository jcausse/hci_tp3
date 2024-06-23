package com.grupo9.easyiot.network

import com.grupo9.easyiot.model.device.DeviceDetails
import com.grupo9.easyiot.model.device.DeviceInfo
import com.grupo9.easyiot.model.device.Devices
import com.grupo9.easyiot.model.device.ExecuteActionResult
import com.grupo9.easyiot.model.routines.ExtecuteResult
import com.grupo9.easyiot.model.routines.Routines
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

const val BASE_URL = "http://192.168.100.87:8080/api/"
const val NOTIFICATION_POLL_TIME_MS: Long = 1000L * 60L // 1 minute

private val httpLoggingInterceptor = HttpLoggingInterceptor()
    .setLevel(HttpLoggingInterceptor.Level.BODY)
private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor).build()

private val json = Json { ignoreUnknownKeys = true
}

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL).client(okHttpClient).build()

interface DeviceApiService {
    @GET("devices")
    suspend fun getDeviceList() : Devices

    @GET("devices/{id}")
    suspend fun getDevice(@Path("id") id: String) : DeviceInfo
}

object DeviceApi {
    val retorfitService : DeviceApiService by lazy {
        retrofit.create(DeviceApiService::class.java)
    }
}

interface DeviceDetailsApiService {
    @GET("devices/{id}")
    suspend fun getDevice(@Path("id") id: String) : DeviceDetails

    @PUT("devices/{id}/{actionName}")
    suspend fun executeAction(
        @Path("id") id: String,
        @Path("actionName") action: String,
        @Body value: List<@JvmSuppressWildcards JsonElement>
    ): ExecuteActionResult



    @PUT("devices/{id}/{actionName}")
    suspend fun executeActionWithoutParameters(
        @Path("id") id: String,
        @Path("actionName") action: String
    ): ExecuteActionResult

    @PUT("devices/{id}/{actionName}")
    suspend fun changeStatus(
        @Path("id") id: String,
        @Path("actionName") action: String
    ): Boolean

}

object DeviceDetailsApi {
    val retorfitService : DeviceDetailsApiService by lazy {
        retrofit.create(DeviceDetailsApiService::class.java)
    }
}

interface RoutineApiService {
    var id : Int
    @GET("routines")
    suspend fun getRoutineList() : Routines

    @PUT("routines/{id}/execute")
    suspend fun executeRoutine(@Path("id") id: String) : ExtecuteResult
}

object RoutineApi {
    val retorfitService : RoutineApiService by lazy {
        retrofit.create(RoutineApiService::class.java)
    }
}

