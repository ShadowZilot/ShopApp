package com.egorponomarev.user_profile.data

import android.content.Context

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
interface UserHandling : UserData.Mapper<Unit> {

    fun clearUserData()

    fun user(): UserData

    fun verifyData(name: String, password: String) : Boolean

    class Base(
        context: Context
    ) : UserHandling {
        private val mDatastore = context.getSharedPreferences(
            "userData",
            Context.MODE_PRIVATE
        )

        override fun clearUserData() {
            mDatastore.edit()
                .putBoolean("isRegister", false)
                .apply()
        }

        override fun user() = if (mDatastore.getBoolean("isRegister", false)) {
            UserData(
                mDatastore.getString(
                    "firstName", ""
                ) ?: throw UserNotRegistered(),
                mDatastore.getString(
                    "lastName", ""
                ) ?: throw UserNotRegistered(),
                mDatastore.getString(
                    "email", ""
                ) ?: throw UserNotRegistered(),
                mDatastore.getString(
                    "photo", ""
                ) ?: throw UserNotRegistered()
            )
        } else {
            throw UserNotRegistered()
        }

        override fun verifyData(name: String, password: String): Boolean {
            val storeName = mDatastore.getString("firstName", "")
            return storeName == name && password == "0000"
        }

        override fun map(
            firstName: String,
            lastName: String,
            email: String,
            photo: String
        ) {
            mDatastore.edit()
                .putString("firstName", firstName)
                .putString("lastName", lastName)
                .putString("email", email)
                .putString("photo", photo)
                .putBoolean("isRegister", true)
                .apply()
        }
    }
}