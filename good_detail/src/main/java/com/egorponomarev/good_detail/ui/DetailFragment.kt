package com.egorponomarev.good_detail.ui

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.egorponomarev.good_detail.R
import com.egorponomarev.good_detail.data.DetailInfo
import com.egorponomarev.good_detail.databinding.DetailFragmentBinding
import com.egorponomarev.good_detail.domain.DetailViewModel
import com.egorponomarev.theme.base.BaseFragment
import com.egorponomarev.theme.base.PriceLabel
import com.egorponomarev.theme.base.ResultLogic
import java.lang.ref.WeakReference

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class DetailFragment : BaseFragment<DetailFragmentBinding>(R.layout.detail_fragment),
    ResultLogic<DetailInfo>, DetailInfo.Mapper<Unit> {
    override val mBinding: DetailFragmentBinding by lazy {
        DetailFragmentBinding.bind(view ?: throw Exception())
    }
    private val mViewModel by viewModels<DetailViewModel> {
        DetailViewModel.Factory()
    }
    private val mColors by lazy {
        ColorSelector.Base(mBinding.colorContainer)
    }
    private val mCarousel by lazy {
        ImageCarousel.Base(
            mBinding.detailImage,
            mBinding.imageCarousel
        )
    }
    private var mPrice = 0f

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mViewModel.loadDetail().collect {
                it.map(this@DetailFragment)
            }
        }
        val changeAmount : (amount: Int) -> Unit = { amount ->
            mBinding.addCartButton.text = requireContext().getString(
                R.string.add_cart_label,
                PriceLabel.Base(
                    WeakReference(mBinding.root.context),
                    mPrice * amount
                ).label()
            )
        }
        mBinding.plusAmountButton.setOnClickListener {
            mViewModel.plusAmount(changeAmount)
        }
        mBinding.minusAmountButton.setOnClickListener {
            mViewModel.minusAmount(changeAmount)
        }
    }

    override fun doIfSuccess(data: DetailInfo) {
        mBinding.detailScreen.visibility = View.GONE
        mBinding.contentErrorLabel.visibility = View.GONE
        mBinding.detailLoading.visibility = View.GONE
        data.map(this)
    }

    override fun doIfFailure(message: Int) {
        mBinding.contentErrorLabel.setText(message)
        mBinding.detailScreen.visibility = View.GONE
        mBinding.contentErrorLabel.visibility = View.VISIBLE
        mBinding.detailLoading.visibility = View.GONE
    }

    override fun doIfLoading() {
        mBinding.detailScreen.visibility = View.GONE
        mBinding.contentErrorLabel.visibility = View.GONE
        mBinding.detailLoading.visibility = View.VISIBLE
    }

    override fun map(
        name: String,
        description: String,
        rating: Float,
        reviewsAmount: Int,
        price: Int,
        colors: List<String>,
        images: List<String>
    ) {
        mPrice = price.toFloat()
        mColors.setup(colors)
        mCarousel.setupImages(images)
        mBinding.detailGoodTitle.text = name
        mBinding.detailDescription.text = description
        mBinding.detailPriceLabel.text = PriceLabel.Base(
            WeakReference(mBinding.root.context),
            price.toFloat()
        ).label()
        mBinding.quantityLabel.text = requireContext().getString(
            R.string.quantity_label,
            mViewModel.amount()
        )
        mBinding.addCartButton.text = requireContext().getString(
            R.string.add_cart_label,
            PriceLabel.Base(
                WeakReference(mBinding.root.context),
                price.toFloat() * mViewModel.amount()
            ).label()
        )
        mBinding.reviewsAndRatingLabel.text = Html.fromHtml(
            "<font color=#161826>$rating</font> <font color=#808080>($reviewsAmount ${
                requireContext().getString(
                    R.string.ratings_reviews_label
                )
            })</font>",
            Html.FROM_HTML_MODE_LEGACY
        )
        mBinding.detailScreen.visibility = View.VISIBLE
    }
}