package com.egorponomarev.user_profile.data

import android.util.Patterns

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class ValidateUserData : UserData.Mapper<Boolean> {

    override fun map(firstName: String, lastName: String,
                     email: String, photo: String): Boolean {
        return firstName.isNotEmpty() && lastName.isNotEmpty() &&
                email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}