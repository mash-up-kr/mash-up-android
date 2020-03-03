package com.mashup.repository.fcm

import android.content.Context
import android.content.SharedPreferences
import com.mashup.api.fcm.request.FcmRegistRequest
import com.mashup.api.fcm.request.FcmTest
import com.mashup.api.fcm.response.FcmRegistResponse
import com.mashup.repository.fcm.remote.FcmRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class DefaultFcmRepository(
    context: Context,
    private val fcmRemoteDataSource: FcmRemoteDataSource
) : FcmRepository {
    private val PREFS_FILENAME = "prefs"
    private val PREF_KEY_FCM_TOKEN = "fcmToken"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    private var cachedFcmToken: String? = null

    override fun getFcmToken(): String? {
        return if (cachedFcmToken != null) {
            cachedFcmToken!!
        } else {
            cachedFcmToken = prefs.getString(PREF_KEY_FCM_TOKEN, null)
            cachedFcmToken
        }
    }

    override fun registFcm(token: String, request: FcmRegistRequest): Single<FcmRegistResponse> {
        cachedFcmToken = request.registrationId
        prefs.edit().putString(PREF_KEY_FCM_TOKEN, cachedFcmToken).apply()
        return fcmRemoteDataSource.registFcm(token, request)
    }

    override fun sendTestNoti(token: String, request: FcmTest): Completable {
        return fcmRemoteDataSource.sendTestNoti(token, request)
    }

    override fun removeFcmRegist(token: String): Completable {
        cachedFcmToken?.let {
            return fcmRemoteDataSource.removeFcmRegist(token, cachedFcmToken!!)
        } ?: return Completable.error(Throwable("not have token"))
    }
}
