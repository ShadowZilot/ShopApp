package com.egorponomarev.user_profile.profile.ui

import android.annotation.SuppressLint
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.egorponomarev.theme.base.BaseFragment
import com.egorponomarev.theme.base.ResultLogic
import com.egorponomarev.theme.base.ShopApp
import com.egorponomarev.theme.base.navigateWithoutBack
import com.egorponomarev.user_profile.R
import com.egorponomarev.theme.user_data.UserData
import com.egorponomarev.theme.user_data.UserHandling
import com.egorponomarev.user_profile.databinding.UserProfileFragmentBinding
import com.egorponomarev.user_profile.profile.domain.UserProfileViewModel

/**
 * Human Developing Soft
 *
 * @author Egor Ponomarev
 */
class UserProfileFragment : BaseFragment<UserProfileFragmentBinding>(
    R.layout.user_profile_fragment
), ResultLogic<UserData>, UserData.Mapper<Unit> {
    override val mBinding: UserProfileFragmentBinding by lazy {
        UserProfileFragmentBinding.bind(view ?: throw Exception())
    }

    private val mViewModel: UserProfileViewModel by viewModels {
        UserProfileViewModel.Factory(UserHandling.Base(requireContext()))
    }

    private val mPhotoPicker = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) {
        context?.contentResolver?.takePersistableUriPermission(
            it ?: Uri.EMPTY,
            FLAG_GRANT_READ_URI_PERMISSION
        )
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            mViewModel.uploadImage(it).collect { result ->
                result.map(this@UserProfileFragment)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity().application as ShopApp).showBottomNav()
        mBinding.logOut.setOnClickListener {
            mViewModel.logOut()
            findNavController().navigateWithoutBack(
                R.id.action_userProfileFragment_to_signInFragment
            )
        }
        mBinding.changePhotoLabel.setOnClickListener {
            mPhotoPicker.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }
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
    override fun map(
        firstName: String, lastName: String,
        email: String, photo: Uri, id: Int
    ) {
        mBinding.userNameLabel.text = "$firstName $lastName"
        if (photo != Uri.EMPTY) {
            mBinding.profileUserPhoto.setImageURI(photo)
        }
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
        findNavController().navigateWithoutBack(
            R.id.action_userProfileFragment_to_signInFragment
        )
    }
}