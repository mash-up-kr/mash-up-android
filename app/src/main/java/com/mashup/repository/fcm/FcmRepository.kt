package com.mashup.repository.fcm

import com.mashup.api.fcm.request.FcmRegistRequest
import com.mashup.api.fcm.request.FcmTest
import com.mashup.api.fcm.response.FcmRegistResponse
import io.reactivex.Completable
import io.reactivex.Single

interface FcmRepository {

    fun registFcm(token: String, request:FcmRegistRequest): Single<FcmRegistResponse>

    fun sendTestNoti(token: String, request:FcmTest): Completable

    fun getFcmToken(): String?

    fun removeFcmRegist(token: String): Completable
}
