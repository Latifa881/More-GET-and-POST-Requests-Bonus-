package com.example.moregetandpostrequests

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("/test/")
    fun getDetails(): Call<Users>

    @Headers("Content-Type: application/json")
    @POST("/test/")
    fun addDetails(@Body user: UsersItem): Call<UsersItem>


}