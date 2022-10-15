package com.swapnildroid.pet.support.ui.main.network

import android.text.TextUtils
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

object ApiClient {

    private val mOkHttpClient = OkHttpClient()

    fun getJsonObject(path: String, apiInterface: ApiInterface) {
        val request =
            Request.Builder().url(ApiEndpoints.BASE_URL + path).build()
        val newCall = mOkHttpClient.newCall(request)
        val responseCallback = object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                apiInterface.onTaskCompleted(ApiResult.FailureApiResult(e.message))
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val body = response.body
                    if (body != null) {
                        parseBody(body, apiInterface, response)
                    }
                } else {
                    apiInterface.onTaskCompleted(ApiResult.FailureApiResult(response.message))
                }
            }
        }
        newCall.enqueue(responseCallback)
    }

    private fun parseBody(body: ResponseBody, apiInterface: ApiInterface, response: Response) {
        val bodyString = body.string()
        if (TextUtils.isEmpty(bodyString)) {
            apiInterface.onTaskCompleted(ApiResult.FailureApiResult(response.message))
        } else {
            try {
                val jsonObject = JSONObject(bodyString)
                apiInterface.onTaskCompleted(ApiResult.SuccessApiResult(jsonObject))
            } catch (e: Exception) {
                apiInterface.onTaskCompleted(ApiResult.FailureApiResult(response.message))
            }
        }
    }

}