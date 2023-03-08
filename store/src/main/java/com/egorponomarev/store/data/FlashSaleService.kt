package com.egorponomarev.store.data

import com.egorponomarev.store.data.dto.FlashSaleItem
import com.egorponomarev.theme.base.ArrayData

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class FlashSaleService(
    private val mDataProvider: ArrayData
) : ContentService<List<FlashSaleItem>> {

    override suspend fun content(): List<FlashSaleItem> {
        val result = mutableListOf<FlashSaleItem>()
        val arrayData = mDataProvider.data()
        for (i in 0 until arrayData.length()) {
            result.add(
                FlashSaleItem(arrayData.getJSONObject(i))
            )
        }
        return result
    }
}
