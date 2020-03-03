package com.mashup.api.fcm

import com.mashup.api.fcm.request.FcmRegistRequest
import com.mashup.api.fcm.request.FcmTest
import com.mashup.api.fcm.response.FcmRegistResponse
import com.mashup.api.notice.request.AttendanceUpdateRequest
import com.mashup.api.notice.response.NoticeListResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.*

interface FcmService {

    @POST("api/push/fcm/")
    @Headers("content-type: application/json")
    fun registFcm(@Header("Authorization") token: String, @Body fcmRegistRequest: FcmRegistRequest): Single<FcmRegistResponse>

    @DELETE("api/push/fcm/{registrationId}/")
    fun removeFcmRegist(@Header("Authorization") token: String, @Path("registrationId") fcmToken: String): Completable

    @POST("api/push/fcm/test/")
    fun sendTestFcm(@Header("Authorization") token: String, @Body fcmTest: FcmTest): Completable
}