package com.mashup.repository.user

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.mashup.api.user.request.AuthTokenRequest
import com.mashup.model.AuthToken
import com.mashup.repository.user.remote.UserRemoteDataSource
import io.reactivex.Flowable
import java.lang.Exception

class DefaultUserRepository(
        context: Context,
        private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    private val PREFS_FILENAME = "prefs"
    private val PREF_KEY_AUTH_TOKEN = "authToken"
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    private var cachedAuthToken: AuthToken? = null

    override fun getCachedAuthToken(): AuthToken? = cachedAuthToken

    override fun hasAuthToken(): Boolean {
        val jsonString = prefs.getString(PREF_KEY_AUTH_TOKEN, "")
        return if (jsonString.isNullOrEmpty()) {
            false
        } else {
            try {
                val authToken = Gson().fromJson(jsonString, AuthToken::class.java)
                cachedAuthToken = authToken
                true
            } catch (error: ClassCastException) {
                false
            } catch (error: Exception) {
                false
            }
        }
    }

    private fun saveAuthToken(authToken: AuthToken) {
        prefs.edit().putString(PREF_KEY_AUTH_TOKEN, Gson().toJson(authToken)).apply()
        cachedAuthToken = authToken
    }

    override fun getAuthToken(request: AuthTokenRequest): Flowable<AuthToken> {
        if (cachedAuthToken != null) {
            return Flowable.just(cachedAuthToken)
        } else {
            val jsonString = prefs.getString(PREF_KEY_AUTH_TOKEN, "")
            if (!jsonString.isNullOrEmpty()) {
                try {
                    val authToken = Gson().fromJson(jsonString, AuthToken::class.java)
                    cachedAuthToken = authToken
                    return Flowable.just(authToken)
                } catch (error: ClassCastException) {
                }
            }
        }

        return userRemoteDataSource
                .getAuthToken(request)
                .doOnNext {
                    saveAuthToken(it)
                }
    }

    override fun logout() {
        cachedAuthToken = null
        prefs.edit().clear().apply()
    }
}
