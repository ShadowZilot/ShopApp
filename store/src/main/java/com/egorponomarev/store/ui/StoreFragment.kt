package com.egorponomarev.store.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.egorponomarev.store.R
import com.egorponomarev.store.databinding.StoreFragmentBinding
import com.egorponomarev.store.domain.StoreResult
import com.egorponomarev.store.domain.StoreViewModel
import com.egorponomarev.theme.base.BaseFragment
import com.egorponomarev.theme.base.ResultLogic
import com.egorponomarev.theme.user_data.UserHandling
import kotlinx.coroutines.flow.collect

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class StoreFragment : BaseFragment<StoreFragmentBinding>(R.layout.store_fragment),
    ResultLogic<StoreResult> {

    override val mBinding: StoreFragmentBinding by lazy {
        StoreFragmentBinding.bind(view ?: throw Exception())
    }
    private val mViewModel by viewModels<StoreViewModel> {
        StoreViewModel.Factory(UserHandling.Base(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mViewModel.photoUri().collect {
                if (it != Uri.EMPTY) {
                    mBinding.userPhoto.setImageURI(it)
                }
            }
        }
    }

    override fun doIfSuccess(data: StoreResult) {
//        mBinding.contentContainer.visibility = View.VISIBLE
//        mBinding.loadingContent.visibility = View.GONE
//        mBinding.contentErrorLabel.visibility = View.GONE
        mBinding.contentContainer.visibility = View.GONE
        mBinding.loadingContent.visibility = View.GONE
        mBinding.contentErrorLabel.visibility = View.VISIBLE
        mBinding.contentErrorLabel.text = "Content loaded successfully!"
    }

    override fun doIfFailure(message: Int) {
        mBinding.contentContainer.visibility = View.GONE
        mBinding.loadingContent.visibility = View.GONE
        mBinding.contentErrorLabel.visibility = View.VISIBLE
        mBinding.contentErrorLabel.setText(message)
    }

    override fun doIfLoading() {
        mBinding.contentContainer.visibility = View.GONE
        mBinding.loadingContent.visibility = View.VISIBLE
        mBinding.contentErrorLabel.visibility = View.GONE
    }
}