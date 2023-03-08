package com.egorponomarev.good_detail.data

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
class DetailService : ContentService<DetailInfo> {

    override suspend fun content(): DetailInfo {
        val response = GlobalClient().newCall(
            Request.Builder()
                .url(ApiEndpoints.sDetailEndpoint)
                .build()
        ).execute()
        return if (response.isSuccessful) {
            val data = DetailInfo(
                JSONObject(
                    response.body?.string() ?: throw Exception()
                )
            )
            response.close()
            data
        } else {
            throw Exception()
        }
    }
}