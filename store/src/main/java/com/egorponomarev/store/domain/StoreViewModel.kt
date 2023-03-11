package com.egorponomarev.store.domain

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.egorponomarev.store.R
import com.egorponomarev.theme.base.ApiEndpoints
import com.egorponomarev.store.data.FlashSaleService
import com.egorponomarev.store.data.LatestService
import com.egorponomarev.store.data.SearchService
import com.egorponomarev.store.data.dto.FlashSaleItem
import com.egorponomarev.store.data.dto.LatestItem
import com.egorponomarev.theme.base.ArrayData
import com.egorponomarev.theme.base.ResultData
import com.egorponomarev.theme.user_data.UserData
import com.egorponomarev.theme.user_data.UserHandling
import com.egorponomarev.theme.user_data.UserNotRegistered
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
typealias StoreResult = Pair<List<LatestItem>, List<FlashSaleItem>>

class StoreViewModel(
    private val mUserHandling: UserHandling
) : ViewModel() {

    fun photoUri(): Flow<Uri> {
        return flow {
            emit(
                try {
                    mUserHandling.user().map(object : UserData.Mapper<Uri> {
                        override fun map(
                            firstName: String,
                            lastName: String,
                            email: String,
                            photo: Uri,
                            id: Int
                        ) = photo
                    })
                } catch (e: UserNotRegistered) {
                    Uri.EMPTY
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    fun searchList() : Flow<List<String>> {
        return flow {
            emit(
                SearchService().content()
            )
        }.flowOn(Dispatchers.IO)
    }

    fun loadContent(): Flow<ResultData<StoreResult>> {
        return flow {
            emit(object : ResultData<StoreResult>(mIsLoading = true) {})
            try {
                val data = Pair(
                    LatestService(
                        ArrayData.Base(
                            ApiEndpoints.sLatestEndpoint,
                            "latest"
                        )
                    ).content(),
                    FlashSaleService(
                        ArrayData.Base(
                            ApiEndpoints.sFlashSaleEndpoint,
                            "flash_sale"
                        )
                    ).content()
                )
                emit(
                    if (data.first.isNotEmpty() && data.second.isNotEmpty()) {
                        object : ResultData<StoreResult>(
                            mData = data
                        ) {}
                    } else {
                        object : ResultData<StoreResult>(
                            mMessage = R.string.something_went_wrong
                        ) {}
                    }
                )
            } catch (e: Exception) {
                emit(object : ResultData<StoreResult>(
                    mMessage = R.string.something_went_wrong
                ) {})
            }
        }.flowOn(Dispatchers.IO)
    }

    class Factory(
        private val mUserHandling: UserHandling
    ) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == StoreViewModel::class.java)
            return StoreViewModel(mUserHandling) as T
        }
    }
}