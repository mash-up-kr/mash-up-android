package com.mashup.util

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.mashup.api.ErrorResponse
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import java.nio.charset.Charset

private val UTF8 = Charset.forName("UTF-8")

class ErrorInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        return if (response.code() >= 400) {
            throwError(response)
            response
        } else {
            response
        }
    }

    @Throws(IOException::class)
    private fun throwError(response: Response) {
        val responseBody = response.body()
        if (responseBody != null) {
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()

            var charset = UTF8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }

            if (responseBody.contentLength() != 0L) {
                val responseJSON = buffer.clone().readString(charset)
                val message: String?
                try {
                    message = Gson().fromJson(responseJSON, ErrorResponse::class.java).message
                    throw Throwable(message)
                } catch (error: JsonParseException) {
                    throw Throwable("")
                }
            }
        } else {
            throw Throwable("")
        }
    }
}