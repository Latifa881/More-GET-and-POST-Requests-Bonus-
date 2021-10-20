package com.example.moregetandpostrequests

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIInterface {
    @Headers("Content-Type: application/json")
    @GET("/contacts/")
    fun getDetails(): Call<ArrayList<Details.Data>>

    @Headers("Content-Type: application/json")
    @POST("/contacts/")
   // fun addNames(@Body name: String): Call<List<Names.Name>>
    fun addDetails(@Body details: Details.Data): Call<Details.Data>
}