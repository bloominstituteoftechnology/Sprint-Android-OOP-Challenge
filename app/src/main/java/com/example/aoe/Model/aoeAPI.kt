package com.example.aoe.Model

import com.example.aoe.viewModel.Structure
import com.example.aoe.viewModel.Unit
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface AoeAPI {

    @GET("people/{id}")
    fun getStructures(@Path("id") id: Int): Call<Structure>

    @GET("starships/{id}")
    fun getUnits(@Path("id") id: Int): Call<Unit>

    class Factory {

        companion object {

            private const val BASE_URL = "https://swapi.co/api/"

            fun create(): AoeAPI {
              val logger = HttpLoggingInterceptor()
                logger.level = HttpLoggingInterceptor.Level.BASIC

                val okHttpClient = OkHttpClient.Builder()
                  .addInterceptor(logger)
                   .retryOnConnectionFailure(false)
                   .readTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                   .build()

                val retrofit = Retrofit.Builder()
                   .client(okHttpClient)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                return retrofit.create(AoeAPI::class.java)
            }
        }
    }
}