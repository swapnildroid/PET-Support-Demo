package com.swapnildroid.pet.support.ui.main.network

import org.json.JSONObject

sealed class ApiResult {

    class SuccessApiResult(val data: JSONObject) : ApiResult()

    class FailureApiResult(val errorMessage: String?) : ApiResult()

}