package com.bangkit.capstone.planitorium.core.data.remote.retrofit

import com.bangkit.capstone.planitorium.core.data.local.Preference
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(private val user: Preference) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { user.getSession().first().token }
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
        return chain.proceed(request)
    }
}