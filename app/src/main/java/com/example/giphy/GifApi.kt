package com.example.giphy

import retrofit2.http.GET
import retrofit2.http.Query
interface GifApi {

    // Search endpoint
    @GET("gifs/search")
    suspend fun searchGifs(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int = 0
    ): GiphyResponse

    // Trending endpoint
    @GET("gifs/trending")
    suspend fun getTrendingGifs(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int = 20
    ): GiphyResponse
}