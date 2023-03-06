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

    class Base(
        context: Context
    ) : UserHandling {
        private val mDatastore = context.getSharedPreferences(
            "userData",
            Context.MODE_PRIVATE
        )

        override fun clearUserData() {
            mDatastore.edit()
                .remove("firstName")
                .remove("lastName")
                .remove("email")
                .remove("photo")
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