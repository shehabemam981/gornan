package com.example.news2024.api

import com.example.news2024.constant.constant
import com.example.news2024.model.Articles
import com.example.news2024.model.Source
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("/v2/top-headlines/sources/")
    fun getSource(@Query("apiKey") apiKey: String = constant.apiKey): Call<Source>

    @GET("/v2/everything/")
    fun getArticles(
        @Query("apiKey") apiKey: String = constant.apiKey, @Query("sources") source: String
    ): Call<Articles>

    @GET("/v2/everything/")
    fun getArticlesFromQuery(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = constant.apiKey
    ): Call<Articles>
    @GET("/v2/top-headlines")
    fun getArticlesFromCategory(@Query("category") category:String,@Query("q") apiKey: String = constant.apiKey):Call<Articles>
}