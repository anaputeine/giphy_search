package com.example.giphy

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GifClient {

    private const val BASE_URL = "https://api.giphy.com/v1/"

    val service: GifApi by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GifApi::class.java)
    }
}