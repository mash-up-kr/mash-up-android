package com.mashup.repository.user

import com.mashup.api.user.request.AuthTokenRequest
import com.mashup.model.AuthToken
import io.reactivex.Flowable

interface UserRepository {

    fun hasAuthToken(): Boolean

    fun getCachedAuthToken(): AuthToken?

    fun getAuthToken(request: AuthTokenRequest): Flowable<AuthToken>
}
