package com.example.sociamediaapplication.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    const val BASE_URL = "https://gerry-unsturdy-wickedly.ngrok-free.dev/" // for emulator


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // ✅ Keep your existing reference
    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }

    // ✅ Add new APIs safely
    val profileApi: ProfileApi by lazy {
        retrofit.create(ProfileApi::class.java)
    }

    val postApi: PostApi by lazy{
        retrofit.create(PostApi::class.java)
    }

    val reelApi: ReelApi by lazy{
        retrofit.create(ReelApi::class.java)
    }

    val productApi: ProductApi by lazy{
        retrofit.create(ProductApi::class.java)
    }

    val paymentApi: PaymentApi by lazy {
        retrofit.create(PaymentApi::class.java)
    }
}
