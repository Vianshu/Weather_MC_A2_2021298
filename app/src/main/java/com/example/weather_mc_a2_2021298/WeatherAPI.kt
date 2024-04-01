package com.example.myapplication
import com.example.myapplication.Model.Weather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/era5?")
    suspend fun getolddata(
        @Query("latitude") latitude:String,
        @Query("longitude") longitude:String,
        @Query("start_date") startdate:String,
        @Query("end_date") enddate:String,
        @Query("hourly") t:String
                 )
    :Response<Weather>;

    @GET("/v1/forecast?")
    suspend fun getl2(
        @Query("latitude") latitude:String,
        @Query("longitude") longitude:String,
        @Query("past_days") pastdays:String,
        @Query("hourly") t:String
    )
    :Response<Weather>
}


