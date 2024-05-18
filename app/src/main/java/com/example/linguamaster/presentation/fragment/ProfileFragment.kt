package com.example.linguamaster.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.linguamaster.R
import com.example.linguamaster.databinding.FragmentProfileBinding
import com.example.linguamaster.presentation.viewmodel.ProfileViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.picasso.transformations.CropCircleTransformation

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val vm: ProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        vm.getProfileData()

        vm.profileDataStateLiveData.observe(viewLifecycleOwner) {
            Picasso.get()
                .load(it.uriAvatar)
                .resize(500, 500).centerCrop().transform(CropCircleTransformation())
                .into(binding.ivAvatar)
            binding.tvUsername.append(it.username)
            binding.tvEmail.append(it.oldEmail)
            binding.tvPremium.append(it.premium)
            binding.tvDateOfBirth.append(it.dateOfBirth)
        }

        binding.btnLogout.setOnClickListener {
            vm.logOut()
            parentFragmentManager.commit {
                replace(R.id.rl, LoginFragment.newInstance(), "LoginFragment")
            }
            requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
                View.GONE
        }

        binding.btnEditProfile.setOnClickListener {
            parentFragmentManager.commit {
                replace(
                    R.id.rl,
                    putDataInFragment(),
                    "s"
                )
                addToBackStack("EditProfileFragment")
            }
        }
        return binding.root
    }

    private fun putDataInFragment(): EditProfileFragment {
        val frag = EditProfileFragment.newInstance()
        val data = Bundle()
        data.putString("data", vm.dataToJson(vm.profileDataStateLiveData.value))
        frag.arguments = data
        return frag
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}