package com.example.porte.Util

import com.example.porte.ValueObject.DepartureResponse
import com.example.porte.ValueObject.DepartureVO
import com.example.porte.ValueObject.ParkingLotResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class ApiService(val url: String) {
    PARKING("http://openapi.airport.kr/openapi/service/StatusOfParking/"),
    DEPARTURE("http://openapi.airport.kr/openapi/service/StatusOfDepartures/")
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

    fun getParkingLotService(service: ApiService): ParkingLotNetwork {
        val retrofit = buildRetrofit(service)
        return retrofit.create(ParkingLotNetwork::class.java)
    }

    fun getDepartureService(service: ApiService): DepartureNetwork {
        val retrofit = buildRetrofit(service)
        return retrofit.create(DepartureNetwork::class.java)
    }
}

interface ParkingLotNetwork {
    @GET("getTrackingParking")

    fun getTop(
        @Query("serviceKey", encoded = true) serviceKey: String,
        @Query("pageNo") pageNo: String,
        @Query("numOfRows") numOfRows: String

    ): Call<ParkingLotResponse>
}

interface DepartureNetwork {
    @GET("getDeparturesCongestion")

    fun getTop(
        @Query("serviceKey", encoded = true) serviceKey: String,
        @Query("terno") terno: String
    ): Call<DepartureResponse>
}