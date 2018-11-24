package com.mashup.api.user

import com.mashup.api.user.request.UserLoginRequest
import com.mashup.api.user.request.UserRegisterRequest
import io.reactivex.Completable
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("/register/member")
    fun register(@Body request: UserRegisterRequest): Completable


    @POST("/login")
    fun login(@Body request: UserLoginRequest): Completable

}