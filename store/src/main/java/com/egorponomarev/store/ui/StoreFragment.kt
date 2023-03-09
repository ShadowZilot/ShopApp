package com.egorponomarev.store.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.egorponomarev.store.R
import com.egorponomarev.store.databinding.StoreFragmentBinding
import com.egorponomarev.store.domain.StoreResult
import com.egorponomarev.store.domain.StoreViewModel
import com.egorponomarev.theme.base.BaseFragment
import com.egorponomarev.theme.base.ResultLogic
import com.egorponomarev.theme.base.navigateWithoutBack
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
        mBinding.flashSaleSection.flashSaleList.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        val detailNavigation: () -> Unit = {
            findNavController().navigateWithoutBack(
                NavDeepLinkRequest.Builder
                    .fromUri(
                        "android-app://com.egorponomarev.shop_adhi/to_details".toUri()
                    )
                    .build()
            )
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mViewModel.searchList().collect {
                mBinding.searchField.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        it.toTypedArray()
                    ).apply {
                        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    }
                )
            }
        }
        mBinding.flashSaleSection.flashSaleList.adapter = FlashSaleAdapter(detailNavigation)
        mBinding.latestSection.latestList.layoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        mBinding.latestSection.latestList.adapter = LatestAdapter(detailNavigation)
    }

    override fun onStart() {
        super.onStart()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mViewModel.loadContent().collect {
                it.map(this@StoreFragment)
            }
            mViewModel.photoUri().collect {
                if (it != Uri.EMPTY) {
                    mBinding.userPhoto.setImageURI(it)
                }
            }
        }
    }

    override fun doIfSuccess(data: StoreResult) {
        (mBinding.latestSection.latestList.adapter as LatestAdapter).fetchData(
            data.first
        )
        (mBinding.flashSaleSection.flashSaleList.adapter as FlashSaleAdapter).fetchData(
            data.second
        )
        mBinding.latestSection
        mBinding.contentContainer.visibility = View.VISIBLE
        mBinding.loadingContent.visibility = View.GONE
        mBinding.contentErrorLabel.visibility = View.GONE
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