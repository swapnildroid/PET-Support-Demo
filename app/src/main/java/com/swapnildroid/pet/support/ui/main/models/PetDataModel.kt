package com.swapnildroid.pet.support.ui.main.models

import org.json.JSONObject

data class PetDataModel(
    var imageUrl: String? = null,
    var title: String? = null,
    var contentUrl: String? = null,
    var dateAdded: String? = null,
) {

    companion object {

        private const val KEY_JSON_IMAGE_URL = "image_url"
        private const val KEY_JSON_TITLE = "title"
        private const val KEY_JSON_CONTENT_URL = "content_url"
        private const val KEY_JSON_DATE_ADDED = "date_added"
        private const val KEY_JSON_PETS_LIST = "pets"

        fun parsePetList(jsonObject: JSONObject): ArrayList<PetDataModel?>? {
            val petList: ArrayList<PetDataModel?> = arrayListOf()
            val petJSONArray = jsonObject.optJSONArray(KEY_JSON_PETS_LIST)
            if (petJSONArray != null) {
                for (index in 0 until petJSONArray.length()) {
                    petList.add(PetDataModel.create(petJSONArray.getJSONObject(index)))
                }
            }
            return petList
        }

        private fun create(jsonObject: JSONObject): PetDataModel {
            return PetDataModel().apply {
                imageUrl = jsonObject.optString(KEY_JSON_IMAGE_URL, "")
                title = jsonObject.optString(KEY_JSON_TITLE, "")
                contentUrl = jsonObject.optString(KEY_JSON_CONTENT_URL, "")
                dateAdded = jsonObject.optString(KEY_JSON_DATE_ADDED, "")
            }

        }

    }

}