package com.egorponomarev.user_profile.login.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.egorponomarev.theme.base.BaseFragment
import com.egorponomarev.theme.base.navigateWithoutBack
import com.egorponomarev.user_profile.R
import com.egorponomarev.theme.user_data.UserHandling
import com.egorponomarev.user_profile.databinding.LoginFragmentBinding
import com.egorponomarev.user_profile.login.domain.LoginViewModel

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class LoginFragment : BaseFragment<LoginFragmentBinding>(R.layout.login_fragment) {
    override val mBinding: LoginFragmentBinding by lazy {
        LoginFragmentBinding.bind(view ?: throw Exception())
    }

    private val mViewModel by viewModels<LoginViewModel> {
        LoginViewModel.Factory(UserHandling.Base(requireContext()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.loginButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                mViewModel.verifyData(
                    mBinding.loginFirstNameField.text.toString(),
                    mBinding.loginPasswordField.text.toString()
                ).collect {
                    if (it) {
                        findNavController().navigateWithoutBack(
                            R.id.action_loginFragment_to_userProfileFragment
                        )
                    } else {
                        Toast.makeText(
                            requireContext(),
                            R.string.wrong_login_data,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}