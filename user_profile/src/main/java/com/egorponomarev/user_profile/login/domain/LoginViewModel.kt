package com.egorponomarev.user_profile.login.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.egorponomarev.theme.user_data.UserHandling
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class LoginViewModel(
    private val mUserHandling: UserHandling
) : ViewModel() {


    fun verifyData(name: String, password: String) : Flow<Boolean> {
        return flow {
            emit(
                mUserHandling.verifyData(name, password)
            )
        }.flowOn(Dispatchers.IO)
    }

    class Factory(
        private val mUserHandling: UserHandling
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == LoginViewModel::class.java)
            return  LoginViewModel(mUserHandling) as T
        }
    }
}