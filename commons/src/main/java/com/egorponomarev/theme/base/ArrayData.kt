package com.egorponomarev.theme.base

import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
interface ArrayData {

    suspend fun data() : JSONArray

    class Base(
        private val mEndpoint: String,
        private val mDataName: String
    ) : ArrayData {

        override suspend fun data(): JSONArray {
            val response = GlobalClient().newCall(
                Request.Builder()
                    .url(mEndpoint)
                    .build()
            ).execute()
            return if (response.isSuccessful) {
                JSONObject(
                    response.body.toString()
                ).getJSONArray(mDataName)
            } else {
                JSONArray()
            }
        }
    }
}