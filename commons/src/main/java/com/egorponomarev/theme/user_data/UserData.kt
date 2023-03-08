package com.egorponomarev.theme.user_data

import android.net.Uri

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
data class UserData(
    private val mFirstName: String,
    private val mLastName: String,
    private val mEmail: String,
    private val mPhoto: Uri
) {

    fun <T> map(mapper: Mapper<T>) = mapper.map(
        mFirstName,
        mLastName,
        mEmail,
        mPhoto
    )

    interface Mapper<T> {
        fun map(
            firstName: String,
            lastName: String,
            email: String,
            photo: Uri
        ) : T
    }
}