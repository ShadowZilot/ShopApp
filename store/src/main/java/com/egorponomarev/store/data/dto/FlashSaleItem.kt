package com.egorponomarev.store.data.dto

import org.json.JSONObject

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
data class FlashSaleItem(
    private val mCategory: String,
    private val mName: String,
    private val mPrice: Float,
    private val mDiscount: Int,
    private val mImageUrl: String
) {
    constructor(item: JSONObject) : this(
        item.getString("category"),
        item.getString("name"),
        item.getDouble("price").toFloat(),
        item.getInt("discount"),
        item.getString("image_url")
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mCategory,
        mName,
        mPrice,
        mDiscount,
        mImageUrl
    )

    interface Mapper<T> {
        fun map(
            category: String,
            name: String,
            price: Float,
            discount: Int,
            imageUrl: String
        ) : T
    }
}