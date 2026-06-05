package com.example.sociamediaapplication.data.remote

import android.provider.MediaStore
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    const val BASE_URL = "http://192.168.1.6:3000/" //"http://dizaartdemo.com/"


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

    val groupApi: GroupApi by lazy {
        retrofit.create(GroupApi::class.java)
    }

    val pageApi: PageApi by lazy {
        retrofit.create(PageApi::class.java)
    }

    val eventApi: EventApi by lazy {
        retrofit.create(EventApi::class.java)
    }

    val jobApi: JobApi by lazy {
        retrofit.create(JobApi::class.java)
    }

    val friendApi: FriendApi by lazy {
        retrofit.create(FriendApi::class.java)
    }

    val storyApi: StoryApi by lazy {
        retrofit.create(StoryApi::class.java)
    }

    val chatApi: ChatApi by lazy {
        retrofit.create(ChatApi::class.java)
    }

    val videoApi: VideoApi by lazy {
        retrofit.create(VideoApi::class.java)
    }

    val notificationApi: NotificationApi by lazy {
        retrofit.create(NotificationApi::class.java)
    }

    val analyticsApi: AnalyticsApi by lazy {
        retrofit.create(AnalyticsApi::class.java)
    }

}
