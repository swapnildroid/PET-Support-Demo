package com.swapnildroid.pet.support.ui.main.viewmodels

import androidx.lifecycle.MutableLiveData
import com.swapnildroid.pet.support.ui.main.models.SettingsDataModel
import com.swapnildroid.pet.support.ui.main.network.ApiClient
import com.swapnildroid.pet.support.ui.main.network.ApiEndpoints
import com.swapnildroid.pet.support.ui.main.network.ApiInterface
import com.swapnildroid.pet.support.ui.main.network.ApiResult
import java.text.SimpleDateFormat
import java.util.*

class ConfigViewModel : BaseViewModel(), ApiInterface {

    val settingsDataModelLiveData: MutableLiveData<SettingsDataModel?> = MutableLiveData(SettingsDataModel())

    fun getConfig() {
        isBusy = true
        ApiClient.getJsonObject(ApiEndpoints.CONFIG_JSON, this)
    }

    override fun onTaskCompleted(apiResult: ApiResult) {
        isBusy = false
        when (apiResult) {
            is ApiResult.SuccessApiResult -> {
                val settingsDataModel = SettingsDataModel.create(apiResult.data)
                settingsDataModelLiveData.postValue(settingsDataModel)
            }
            is ApiResult.FailureApiResult -> errorInterface?.onError(apiResult.errorMessage)
        }
    }

    private val hrsSDF = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val weekDaySDF = SimpleDateFormat("E", Locale.getDefault())
    private val weekDayArray = arrayOf("S", "M", "T", "W", "T", "F", "S")

    fun isWorkingHours(): Boolean {
        try {
            val workingHoursString = "${settingsDataModelLiveData.value?.workHours}"

            val weekDayStart = workingHoursString.split(" ")[0].split("-")[0]
            val weekDayEnd = workingHoursString.split(" ")[0].split("-")[1]

            val indexOfFirstStart = weekDayArray.indexOfFirst { it == weekDayStart }
            val indexOfFirstEnd = weekDayArray.indexOfFirst { it == weekDayEnd }

            val workWeekArray = mutableListOf<String>()

            for (i in indexOfFirstStart..indexOfFirstEnd) workWeekArray.add(weekDayArray[i])

            val now = Calendar.getInstance()

            val weekDayNow = weekDaySDF.format(now.time).substring(0, 1)
            val hrsNow = hrsSDF.format(now.time).replace(":", "").toInt()
            val hrsStart = workingHoursString.split(" ")[1].replace(":", "").toInt()
            val hrsEnd = workingHoursString.split(" ")[3].replace(":", "").toInt()

            var isWorkDay = false
            if (workWeekArray.contains(weekDayNow)) {
                if (hrsNow in hrsStart..hrsEnd) {
                    isWorkDay = true
                }
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return true
    }

}