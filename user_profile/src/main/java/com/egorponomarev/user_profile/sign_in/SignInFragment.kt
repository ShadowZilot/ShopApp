package com.egorponomarev.user_profile.sign_in

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.egorponomarev.theme.base.BaseFragment
import com.egorponomarev.user_profile.R
import com.egorponomarev.user_profile.databinding.SignInFragmentBinding

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class SignInFragment : BaseFragment<SignInFragmentBinding>(R.layout.sign_in_fragment) {
    override val mBinding: SignInFragmentBinding by lazy {
        SignInFragmentBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.signInButton.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Button clicked!",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}