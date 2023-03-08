package com.egorponomarev.good_detail.data

import org.json.JSONObject

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
data class DetailInfo(
    private val mName: String,
    private val mDescription: String,
    private val mRating: Float,
    private val mReviewsAmount: Int,
    private val mPrice: Int,
    private val mColors: List<String>,
    private val mImages: List<String>
) {
    constructor(item: JSONObject) : this(
        item.getString("name"),
        item.getString("description"),
        item.getDouble("rating").toFloat(),
        item.getInt("number_of_reviews"),
        item.getInt("price"),
        mutableListOf<String>().apply {
            val colorsArray = item.getJSONArray("colors")
            for (i in 0 until colorsArray.length()) {
                add(colorsArray.getString(i))
            }
        },
        mutableListOf<String>().apply {
            val imagesArray = item.getJSONArray("image_urls")
            for (i in 0 until imagesArray.length()) {
                add(imagesArray.getString(i))
            }
        }
    )

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mName,
        mDescription,
        mRating,
        mReviewsAmount,
        mPrice,
        mColors,
        mImages
    )

    interface Mapper<T> {

        fun map(
            name: String,
            description: String,
            rating: Float,
            reviewsAmount: Int,
            price: Int,
            colors: List<String>,
            images: List<String>
        ) : T
    }
}