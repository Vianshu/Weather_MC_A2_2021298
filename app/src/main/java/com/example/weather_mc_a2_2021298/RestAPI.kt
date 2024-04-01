package com.example.myapplication

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.Call
import com.example.myapplication.WeatherApi
import retrofit2.create

object RestAPIInstance{
    val apiold:WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://archive-api.open-meteo.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
    val apil2:WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.open-meteo.com/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}

//
//class RestAPI() {
//    private val retrofit: WeatherApi = TODO()
//
//    init {
//        Retrofit.Builder()
//            .baseUrl("https://archive-api.open-meteo.com")
//            .addConverterFactory(MoshiConverterFactory.create())
//            .build()
//            .create(WeatherApi::class.java)
//        retrofit.create(WeatherApi::class.java).also { WeatherApi = it }
//    }
//
//    fun getd(a:String,b:String,sd:String,ed:String):Call<Wapiresponse>{
//        return WeatherApi.getdata(a,b,sd,ed)
//    }
//}
//
