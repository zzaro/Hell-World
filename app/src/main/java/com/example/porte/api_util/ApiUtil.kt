package com.example.porte.api_util

import com.example.porte.ValueObject.ParkingLotResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

enum class ApiService(val url: String) {
    PARKING("http://openapi.airport.kr/openapi/service/StatusOfParking/")
}


object ApiUtil {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    fun buildRetrofit(service: ApiService): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(service.url)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitBuilder
    }

    fun getParkingLotService(service: ApiService): ParkingLotInfoNetwork {
        val retrofit = buildRetrofit(service)
        return retrofit.create(ParkingLotInfoNetwork::class.java)
    }
}

interface ParkingLotInfoNetwork {
    @GET("getTrackingParking")

    fun getTop(
        @Query("serviceKey", encoded = true) serviceKey: String,
        @Query("pageNo") pageNo: String,
        @Query("numOfRows") numOfRows: String

    ): Call<ParkingLotResponse>
}