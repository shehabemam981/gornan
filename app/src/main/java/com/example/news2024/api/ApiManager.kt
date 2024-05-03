package com.example.news2024.api


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object apiManager {
    var retrofit: Retrofit? = null
    fun getRetroFit(): Retrofit {
        if (retrofit == null) {
            retrofit = Retrofit.Builder().client(getClient()).baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
        return retrofit!!
    }
    fun getClient():OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return client
    }
  fun  getSource(): WebService {
      return  getRetroFit().create(WebService::class.java)
  }

}