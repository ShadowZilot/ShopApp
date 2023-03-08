package com.egorponomarev.good_detail.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.egorponomarev.good_detail.R
import com.egorponomarev.good_detail.data.DetailInfo
import com.egorponomarev.good_detail.data.DetailService
import com.egorponomarev.theme.base.ContentService
import com.egorponomarev.theme.base.ResultData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class DetailViewModel(
    private val mDetailProvider: ContentService<DetailInfo>
) : ViewModel() {
    private var mAmountOfGood = 1

    suspend fun loadDetail(): Flow<ResultData<DetailInfo>> {
        return flow {
            emit(object : ResultData<DetailInfo>(mIsLoading = true) {})
            emit(
                try {
                    val data = mDetailProvider.content()
                    object : ResultData<DetailInfo>(mData = data) {}
                } catch (e: Exception) {
                    object : ResultData<DetailInfo>(mMessage = R.string.detail_error_text) {}
                }
            )
        }.flowOn(Dispatchers.IO)
    }

    fun plusAmount(listener: (newAmount: Int) -> Unit) {
        mAmountOfGood++
        listener.invoke(mAmountOfGood)
    }

    fun minusAmount(listener: (newAmount: Int) -> Unit) {
        mAmountOfGood--
        listener.invoke(mAmountOfGood)
    }

    fun amount() : Int = mAmountOfGood

    class Factory : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            require(modelClass == DetailViewModel::class.java)
            return DetailViewModel(
                DetailService()
            ) as T
        }
    }
}