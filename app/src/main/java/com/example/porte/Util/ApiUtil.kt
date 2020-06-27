package com.example.porte.Util

import com.example.porte.ValueObject.DepartureResponse
import com.example.porte.ValueObject.FlightResponse
import com.example.porte.ValueObject.ParkingLotResponse
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// API별 BaseURL 열거형
enum class ApiService(val url: String) {
    PARKING("http://openapi.airport.kr/openapi/service/StatusOfParking/"),
    DEPARTURE("http://openapi.airport.kr/openapi/service/StatusOfDepartures/"),
    Flight("http://openapi.airport.kr/openapi/service/StatusOfPassengerFlightsDS/")
}


object ApiUtil {
    var gson = GsonBuilder()
        .setLenient()
        .create()

    // 레트로핏 빌더
    fun buildRetrofit(service: ApiService): Retrofit {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(service.url)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitBuilder
    }

    // 주차장 현황 레트로핏 빌더
    fun getParkingLotService(service: ApiService): ParkingLotNetwork {
        val retrofit = buildRetrofit(service)
        return retrofit.create(ParkingLotNetwork::class.java)
    }
    // 출국장 현황 레트로핏 빌더
    fun getDepartureService(service: ApiService): DepartureNetwork {
        val retrofit = buildRetrofit(service)
        return retrofit.create(DepartureNetwork::class.java)
    }
    // 항공 현황 레트로핏 빌더
    fun getFlightService(service: ApiService): FlightNetwork {
        val retrofit = buildRetrofit(service)
        return retrofit.create(FlightNetwork::class.java)
    }
}

// 주차장 정보
interface ParkingLotNetwork {
    @GET("getTrackingParking")

    fun getTop(
        @Query("serviceKey", encoded = true) serviceKey: String,
        @Query("pageNo") pageNo: String,
        @Query("numOfRows") numOfRows: String

    ): Call<ParkingLotResponse>
}

// 출국장 정보
interface DepartureNetwork {
    @GET("getDeparturesCongestion")

    fun getTop(
        @Query("serviceKey", encoded = true) serviceKey: String,
        @Query("terno") terno: String
    ): Call<DepartureResponse>
}

// 항공편 정보
interface FlightNetwork {
    @GET("getPassengerDeparturesDS")

    fun getTop(
        @Query("serviceKey", encoded = true) serviceKey: String,
        @Query("airport_code") airiport_code: String
    ): Call<FlightResponse>
}

