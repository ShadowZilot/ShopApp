package com.egorponomarev.user_profile.data

import android.net.Uri

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class InjectPhotoToUserData(
    private val mPhoto: Uri
) : UserData.Mapper<UserData> {

    override fun map(
        firstName: String, lastName: String,
        email: String, photo: Uri
    ) = UserData(
        firstName,
        lastName,
        email,
        mPhoto
    )
}