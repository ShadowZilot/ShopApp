package com.egorponomarev.store.data

import com.egorponomarev.theme.base.ApiEndpoints
import com.egorponomarev.theme.base.ContentService
import com.egorponomarev.theme.base.GlobalClient
import okhttp3.Request
import org.json.JSONObject

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class SearchService : ContentService<List<String>> {

    override suspend fun content(): List<String> {
        val response = GlobalClient().newCall(
            Request.Builder()
                .url(ApiEndpoints.sSearchEndpoint)
                .build()
        ).execute()
        return if (response.isSuccessful) {
            val result = mutableListOf<String>()
            val dataArray = JSONObject(response.body?.string() ?: throw Exception())
                .getJSONArray("words")
            for (i in 0 until dataArray.length()) {
                result.add(dataArray.getString(i))
            }
            response.close()
            result
        } else {
            emptyList()
        }
    }
}