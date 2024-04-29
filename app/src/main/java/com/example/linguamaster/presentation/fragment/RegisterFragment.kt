package com.example.linguamaster.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import com.example.linguamaster.R
import com.example.linguamaster.databinding.FragmentRegisterBinding
import com.example.linguamaster.domain.model.RegistrationFormState
import com.example.linguamaster.presentation.viewmodel.RegisterViewModel


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var vm: RegisterViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this)[RegisterViewModel::class.java]

        binding.btmSignUp.setOnClickListener {
            vm.submitData(
                RegistrationFormState(
                    binding.etUsername.text.toString(),
                    null,
                    binding.etEmail.text.toString(),
                    null,
                    binding.etPassword.text.toString(),
                    null,
                    binding.etConfirmPassword.text.toString(),
                    null
                )
            )
        }

        vm.usernameLiveData.observe(viewLifecycleOwner) {
            binding.etUsername.error = it
        }
        vm.emailLiveData.observe(viewLifecycleOwner) {
            binding.etEmail.error = it
        }
        vm.passwordLiveData.observe(viewLifecycleOwner) {
            binding.etPassword.error = it
        }
        vm.confirmPasswordLiveData.observe(viewLifecycleOwner) {
            binding.etConfirmPassword.error = it
        }
        vm.successRegisterLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Success register", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            parentFragmentManager.commit {
                replace(R.id.rl, HomeFragment.newInstance(), "HomeFragment")
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}