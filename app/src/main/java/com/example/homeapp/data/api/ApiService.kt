package com.example.homeapp.data.api

import com.example.homeapp.data.model.NewsResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("api/1/latest")
    fun getEcommerceNews(
        @Query("apikey") apiKey: String = "pub_a33fd8cddf0242f39f0d5c0e977b67fe",
        @Query("q") query: String = "e-commerce"
    ): Call<NewsResponse>
}

object ApiClient {
    private const val BASE_URL = "https://newsdata.io/"

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ApiService::class.java)
    }
}