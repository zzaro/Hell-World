package com.example.porte.api_util

import com.example.porte.ValueObject.ParkingLotModel
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


object ParkingLotAPI {

    var gson = GsonBuilder()
        .setLenient()
        .create()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://openapi.airport.kr/openapi/service/StatusOfParking/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getService(): ParkingLotInfoNetwork {
        return retrofit.create(ParkingLotInfoNetwork::class.java)
    }
}

interface ParkingLotInfoNetwork {
    @GET("getTrackingParking")

    fun getTop(
        @Query("serviceKey") serviceKey: String,
        @Query("pageNo") pageNo: String,
        @Query("numOfRows") numOfRows: String
    ): Call<ParkingLotModel>
}