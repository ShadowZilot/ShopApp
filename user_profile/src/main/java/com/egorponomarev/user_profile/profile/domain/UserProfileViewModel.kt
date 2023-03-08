package com.egorponomarev.user_profile.profile.domain

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.egorponomarev.theme.base.ResultData
import com.egorponomarev.user_profile.R
import com.egorponomarev.theme.user_data.InjectPhotoToUserData
import com.egorponomarev.theme.user_data.UserData
import com.egorponomarev.theme.user_data.UserHandling
import com.egorponomarev.theme.user_data.UserNotRegistered
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class UserProfileViewModel(
    private val mUserHandling: UserHandling
) : ViewModel() {

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            mUserHandling.clearUserData()
        }
    }

    fun uploadImage(image: Uri?): Flow<ResultData<UserData>> {
        return flow {
            emit(
                if (image != null) {
                    val newData = mUserHandling.user().map(InjectPhotoToUserData(image))
                    newData.map(mUserHandling)
                    object : ResultData<UserData>(mData = mUserHandling.user()) {}
                } else {
                    object : ResultData<UserData>(mMessage = R.string.photo_not_selected) {}
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    fun loadUserData(): Flow<ResultData<UserData>> {
        return flow {
            emit(try {
                object : ResultData<UserData>(mData = mUserHandling.user()) {}
            } catch (e: UserNotRegistered) {
                object : ResultData<UserData>(mMessage = R.string.user_not_registed) {}
            })
        }.flowOn(Dispatchers.IO)
    }

    class Factory(
        private val mUserHandling: UserHandling
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == UserProfileViewModel::class.java)
            return UserProfileViewModel(mUserHandling) as T
        }
    }
}