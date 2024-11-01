package com.example.linguamaster.presentation.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.linguamaster.databinding.FragmentEditProfileBinding
import com.example.linguamaster.domain.model.ProfileData
import com.example.linguamaster.presentation.viewmodel.EditProfileViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.picasso.transformations.CropCircleTransformation


@AndroidEntryPoint
class EditProfileFragment : Fragment() {

    private lateinit var binding: FragmentEditProfileBinding
    private val vm: EditProfileViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
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
        vm.profileBanksStateLiveData.observe(viewLifecycleOwner) {
            Picasso.get()
                .load(it.uriAvatar)
                .resize(500, 500).centerCrop().transform(CropCircleTransformation())
                .into(binding.ivAvatar)
            binding.etUsername.setText(it.username)
            binding.etOldEmail.setText(it.oldEmail)
            binding.etDateOfBirth.setText(it.dateOfBirth)
        }
        observersLiveData()
        binding.btnUpdate.setOnClickListener {
            vm.updateData(getData())
        }

        return binding.root
    }

    private fun observersLiveData() {
        vm.usernameLiveData.observe(viewLifecycleOwner) {
            binding.etUsername.error = it
        }
        vm.emailLiveData.observe(viewLifecycleOwner) {
            binding.etOldEmail.error = it
        }
        vm.dateOfBirthLiveData.observe(viewLifecycleOwner) {
            binding.etDateOfBirth.error = it
        }
        vm.successUpdateLiveData.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Update was successful complete", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getData(): ProfileData {
        return ProfileData(
            uriAvatar = binding.ivAvatar.toString(),
            username = binding.etUsername.text.toString(),
            oldEmail = binding.etOldEmail.text.toString(),
            newEmail = binding.etNewEmail.text.toString(),
            oldPassword = binding.etOldPassword.text.toString(),
            newPassword = binding.etNewPassword.text.toString(),
            dateOfBirth = binding.etDateOfBirth.text.toString(),
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = EditProfileFragment()
    }
}