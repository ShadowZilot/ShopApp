package com.egorponomarev.store.data

import com.egorponomarev.store.data.dto.LatestItem
import com.egorponomarev.theme.base.ArrayData

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class LatestService(
    private val mDataProvider: ArrayData
) : ContentService<List<LatestItem>> {

    override suspend fun content(): List<LatestItem> {
        val result = mutableListOf<LatestItem>()
        val arrayData = mDataProvider.data()
        for (i in 0 until arrayData.length()) {
            result.add(
                LatestItem(arrayData.getJSONObject(i))
            )
        }
        return result
    }
}