package com.example.jph.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkService {
    private val BASE_URL = "https://jsonplaceholder.typicode.com"
    private var mRetrofit: Retrofit? = null

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getJSONApi(): JSONPlaceHolderApi {
        return mRetrofit!!.create(JSONPlaceHolderApi::class.java)
    }
}