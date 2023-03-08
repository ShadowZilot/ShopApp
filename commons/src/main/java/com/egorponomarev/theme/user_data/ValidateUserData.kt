package com.egorponomarev.theme.user_data

import android.net.Uri
import android.util.Patterns

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class ValidateUserData : UserData.Mapper<Boolean> {

    override fun map(
        firstName: String, lastName: String,
        email: String, photo: Uri
    ): Boolean {
        return firstName.isNotEmpty() && lastName.isNotEmpty() &&
                email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}