package com.kioko.testapp.api

import com.kioko.testapp.api.data.PaymentsResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

public val okHttpClient: OkHttpClient
    get() = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

interface MyApi {
    @GET("/v3/893b142b-ec1e-4fd1-b04d-bee0e1a21406")
    fun getPayments(): Call<PaymentsResponse>

    companion object{
        operator fun invoke(): MyApi{
            return Retrofit.Builder()
                .baseUrl("https://run.mocky.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(MyApi::class.java)
        }
    }
}