package com.egorponomarev.user_profile.profile.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.egorponomarev.theme.base.BaseFragment
import com.egorponomarev.theme.base.ResultLogic
import com.egorponomarev.user_profile.R
import com.egorponomarev.user_profile.data.UserData
import com.egorponomarev.user_profile.data.UserHandling
import com.egorponomarev.user_profile.databinding.UserProfileFragmentBinding
import com.egorponomarev.user_profile.profile.domain.UserProfileViewModel

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class UserProfileFragment : BaseFragment<UserProfileFragmentBinding>(
    R.layout.user_profile_fragment
), ResultLogic<UserData>, UserData.Mapper<Unit>{
    override val mBinding: UserProfileFragmentBinding by lazy {
        UserProfileFragmentBinding.bind(view ?: throw Exception())
    }

    private val mViewModel : UserProfileViewModel by viewModels {
        UserProfileViewModel.Factory(UserHandling.Base(requireContext()))
    }

    override fun onStart() {
        super.onStart()
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mViewModel.loadUserData().collect {
                it.map(this@UserProfileFragment)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun map(firstName: String, lastName: String,
                     email: String, photo: String) {
        mBinding.userNameLabel.text = "$firstName $lastName"
    }

    override fun doIfSuccess(data: UserData) {
        data.map(this)
    }

    override fun doIfFailure(message: Int) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
        findNavController().navigate(
            R.id.action_userProfileFragment_to_signInFragment
        )
    }
}