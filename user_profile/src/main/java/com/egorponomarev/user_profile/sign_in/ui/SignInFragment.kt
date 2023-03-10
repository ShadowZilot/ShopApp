package com.egorponomarev.user_profile.sign_in.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.egorponomarev.theme.base.BaseFragment
import com.egorponomarev.theme.base.ShopApp
import com.egorponomarev.theme.base.navigateWithoutBack
import com.egorponomarev.user_profile.R
import com.egorponomarev.theme.user_data.UserData
import com.egorponomarev.theme.user_data.UserHandling
import com.egorponomarev.user_profile.databinding.SignInFragmentBinding
import com.egorponomarev.user_profile.sign_in.domain.SignInViewModel

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class SignInFragment : BaseFragment<SignInFragmentBinding>(R.layout.sign_in_fragment) {
    override val mBinding: SignInFragmentBinding by lazy {
        SignInFragmentBinding.bind(view ?: throw Exception())
    }

    private val mViewModel: SignInViewModel by viewModels {
        SignInViewModel.Factory(UserHandling.Base(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity().application as ShopApp).hideBottomNav()
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            mViewModel.checkUserRegistration().collect {
                if (it) {
                    findNavController().navigateWithoutBack(
                        R.id.action_signInFragment_to_userProfileFragment
                    )
                }
            }
        }
        mBinding.existAccountButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_signInFragment_to_loginFragment
            )
        }
        mBinding.signInButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                mViewModel.signInUser(
                    UserData(
                        -1,
                        mBinding.signInFirstNameField.text.toString(),
                        mBinding.signInLastNameField.text.toString(),
                        mBinding.signInEmailField.text.toString(),
                        Uri.EMPTY
                    )
                ).collect { result ->
                    if (!result) {
                        Toast.makeText(
                            requireContext(),
                            R.string.user_data_wrong_label,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        findNavController().navigateWithoutBack(
                            R.id.action_signInFragment_to_userProfileFragment
                        )
                    }
                }
            }
        }
    }
}