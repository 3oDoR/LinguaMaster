package com.example.linguamaster.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.linguamaster.databinding.FragmentEditProfileBinding
import com.example.linguamaster.presentation.viewmodel.EditProfileViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.picasso.transformations.CropCircleTransformation


@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val vm: EditProfileViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val bundle = arguments?.getString("data")
        if (bundle != null) {
            vm.replaceStringToClass(bundle)
        }
        vm.profileBanksLiveData.observe(viewLifecycleOwner) {
            Picasso.get()
                .load(it.uriAvatar)
                .resize(500, 500).centerCrop().transform(CropCircleTransformation())
                .into(binding.ivAvatar)
            binding.etUsername.setText(it.username)
            binding.etEmail.setText(it.email)
            binding.etPhoneNumber.setText(it.phoneNumber)
            binding.etDateOfBirth.setText(it.dateOfBirth)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditProfileFragment()
    }
}