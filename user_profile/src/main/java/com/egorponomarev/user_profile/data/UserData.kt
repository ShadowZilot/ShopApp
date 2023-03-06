package com.egorponomarev.user_profile.data

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
data class UserData(
    private val mFirstName: String,
    private val mLastName: String,
    private val mEmail: String,
    private val mPhoto: String
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
            photo: String
        ) : T
    }
}