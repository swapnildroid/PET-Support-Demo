package com.swapnildroid.pet.support.ui.main.models

import android.view.View
import org.json.JSONObject

data class SettingsDataModel(
    var isChatEnabled: Int = View.GONE,
    var isCallEnabled: Int = View.GONE,
    var workHours: String? = null
) {

    companion object {

        private const val KEY_JSON_SETTINGS = "settings"
        private const val KEY_JSON_IS_CALL_ENABLED = "isCallEnabled"
        private const val KEY_JSON_IS_CHAT_ENABLED = "isCallEnabled"
        private const val KEY_JSON_WORK_HOURS = "workHours"

        fun create(jsonObject: JSONObject): SettingsDataModel {
            val settingsOptJSONObject = jsonObject.optJSONObject(KEY_JSON_SETTINGS)
            val settingsDataModel = SettingsDataModel()
            settingsDataModel.isCallEnabled = if (settingsOptJSONObject?.optBoolean(
                    KEY_JSON_IS_CALL_ENABLED,
                    false
                ) == true
            ) View.VISIBLE else View.GONE
            settingsDataModel.isChatEnabled = if (settingsOptJSONObject?.optBoolean(
                    KEY_JSON_IS_CHAT_ENABLED,
                    false
                ) == true
            ) View.VISIBLE else View.GONE

            settingsDataModel.workHours =
                settingsOptJSONObject?.optString(KEY_JSON_WORK_HOURS, null)
            return settingsDataModel
        }

    }

}