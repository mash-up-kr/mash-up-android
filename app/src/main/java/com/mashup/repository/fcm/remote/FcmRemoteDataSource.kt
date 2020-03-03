package com.mashup.repository.fcm.remote

import com.mashup.api.fcm.FcmService
import com.mashup.api.fcm.request.FcmRegistRequest
import com.mashup.api.fcm.request.FcmTest
import com.mashup.api.fcm.response.FcmRegistResponse
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FcmRemoteDataSource internal constructor(
    private val fcmService: FcmService
) {
    fun registFcm(token: String, request: FcmRegistRequest): Single<FcmRegistResponse> =
        fcmService.registFcm("Token $token", request).subscribeOn(Schedulers.io())

    fun sendTestNoti(token: String, request: FcmTest): Completable =
        fcmService.sendTestFcm("Token $token", request).subscribeOn(Schedulers.io())

    fun removeFcmRegist(token: String, fcmToken: String): Completable =
        fcmService.removeFcmRegist("Token $token", fcmToken).subscribeOn(Schedulers.io())
}
