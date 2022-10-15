package com.swapnildroid.pet.support.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import com.swapnildroid.pet.support.ui.main.models.PetDataModel
import com.swapnildroid.pet.support.ui.main.network.ApiClient
import com.swapnildroid.pet.support.ui.main.network.ApiEndpoints
import com.swapnildroid.pet.support.ui.main.network.ApiInterface
import com.swapnildroid.pet.support.ui.main.network.ApiResult

class PetListViewModel : BaseViewModel(), ApiInterface {

    val petListLiveData: MutableLiveData<ArrayList<PetDataModel?>?> = MutableLiveData(arrayListOf())

    fun getPetList() {
        isBusy = true
        ApiClient.getJsonObject(ApiEndpoints.PET_JSON, this)
    }

    override fun onTaskCompleted(apiResult: ApiResult) {
        isBusy = false
        when (apiResult) {
            is ApiResult.SuccessApiResult -> {
                val arrayList = PetDataModel.parsePetList(apiResult.data)
                petListLiveData.postValue(arrayList)
            }
            is ApiResult.FailureApiResult -> errorInterface?.onError(apiResult.errorMessage)
        }
    }

}