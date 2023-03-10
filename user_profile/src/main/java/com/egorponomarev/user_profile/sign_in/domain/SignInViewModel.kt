package com.egorponomarev.user_profile.sign_in.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.egorponomarev.theme.user_data.UserData
import com.egorponomarev.theme.user_data.UserHandling
import com.egorponomarev.theme.user_data.UserNotRegistered
import com.egorponomarev.theme.user_data.ValidateUserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class SignInViewModel(
    private val mUserData: UserHandling
) : ViewModel() {

    fun checkUserRegistration(): Flow<Boolean> {
        return flow {
            emit(
                try {
                    mUserData.user()
                    true
                } catch (e: UserNotRegistered) {
                    false
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    fun signInUser(userData: UserData): Flow<Boolean> {
        return flow {
            if (userData.map(ValidateUserData())) {
                userData.map(mUserData)
                emit(true)
            } else {
                emit(false)
            }
        }.flowOn(Dispatchers.IO)
    }

    class Factory(
        private val mUserHandling: UserHandling
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == SignInViewModel::class.java)
            return SignInViewModel(mUserHandling) as T
        }
    }
}