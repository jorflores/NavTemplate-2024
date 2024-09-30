package com.example.navtemplate.service


import com.example.navtemplate.BuildConfig
import com.example.navtemplate.data.LoginUserRequest
import com.example.navtemplate.data.LoginUserResponse
import com.example.navtemplate.data.RegisterUserRequest
import com.example.navtemplate.data.RegisterUserResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface UserService {



    companion object {
        private val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }



        private val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()


        val instance: UserService = Retrofit.Builder()
            
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(UserService::class.java)
    }

    @POST("api/users/register")
    suspend fun addUser(@Body user: RegisterUserRequest) : RegisterUserResponse


    @POST("api/users/login")
    suspend fun loginUser(@Body user: LoginUserRequest) : LoginUserResponse

}