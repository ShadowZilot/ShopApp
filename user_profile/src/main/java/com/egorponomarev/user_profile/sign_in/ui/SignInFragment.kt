package com.egorponomarev.user_profile.sign_in.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.egorponomarev.theme.base.BaseFragment
import com.egorponomarev.user_profile.R
import com.egorponomarev.user_profile.data.UserData
import com.egorponomarev.user_profile.data.UserHandling
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
        mBinding.signInButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                mViewModel.signInUser(
                    UserData(
                        mBinding.signInFirstNameField.text.toString(),
                        mBinding.signInLastNameField.text.toString(),
                        mBinding.signInEmailField.text.toString(),
                        ""
                    )
                ).collect { result ->
                    if (!result) {
                        Toast.makeText(
                            requireContext(),
                            R.string.user_data_wrong_label,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}