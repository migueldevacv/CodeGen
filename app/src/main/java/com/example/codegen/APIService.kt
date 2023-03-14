package com.example.codegen

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface APIService {
    @Headers("Content-Type: application/json")
    @POST("api/validate/aplication/code")
    fun validateCode(@Body requestBody: Map<String,String>): Call<CodeResponse>
}