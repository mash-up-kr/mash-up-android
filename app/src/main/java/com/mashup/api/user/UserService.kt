package com.mashup.api.user

import com.mashup.api.user.request.AuthTokenRequest
import com.mashup.model.AuthToken
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("api/members/auth-token/")
    fun getAuthToken(@Body request: AuthTokenRequest): Flowable<AuthToken>

}