package com.online_shopping.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val baseUrl = "https://www.jsonbulut.com/json/"
    val ref : String = "c7c2de28d81d3da4a386fc8444d574f2"
    private var retrofit : Retrofit? = null
    fun getClient() : Retrofit {
        if (retrofit == null){
            retrofit = Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}