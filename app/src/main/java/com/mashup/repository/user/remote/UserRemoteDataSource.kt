package com.mashup.repository.user.remote

import com.mashup.api.user.UserService
import com.mashup.api.user.request.AuthTokenRequest
import com.mashup.model.AuthToken
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class UserRemoteDataSource internal constructor(
    private val userService: UserService
) {
    fun getAuthToken(request: AuthTokenRequest): Flowable<AuthToken> =
        userService.getAuthToken(request).subscribeOn(Schedulers.io())
}
